package com.tensquare.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.tensquare.user.pojo.User;
import com.tensquare.user.service.UserService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import util.JwtUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 查询全部数据
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", userService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", userService.findById(id));
    }

    /**
     * 分页+多条件查询
     *
     * @param searchMap 查询条件封装
     * @param page      页码
     * @param size      页大小
     * @return 分页结果
     */
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<User> pageList = userService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<User>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(true, StatusCode.OK, "查询成功", userService.findSearch(searchMap));
    }

    /**
     * 增加
     *
     * @param user
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody User user) {
        userService.add(user);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /**
     * 修改
     *
     * @param user
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody User user, @PathVariable String id) {
        user.setId(id);
        userService.update(user);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    @Autowired
    private HttpServletRequest request;

    /**
     * 调用微服务方法先要经过拦截器
     * 删除,后台管理员账户才有权限进行删除
     * 前后端约定：前端提交url在请求头设置token  形式： key:"Authorization"  value:"Bearer "+token
     * @param id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        Claims adminClaims = (Claims) request.getAttribute("admin_claims");
        if(adminClaims!=null){
            //有admin角色 执行删除
            userService.deleteById(id);
            return new Result(true, StatusCode.OK, "删除成功");
        }
        return new Result(false, StatusCode.ACCESS_ERROR, "您无权进行操作");
        //获取请求头信息
       /* String header = request.getHeader("Authorization");
        if(header!=null && header.startsWith("Bearer ")){
            String token = header.substring(7);
            //解析token
            Claims claims = jwtUtil.parseJwt(token);
            String role = (String) claims.get("role");
            if("admin".equals(role)){
                //有admin角色 执行删除
                userService.deleteById(id);
                return new Result(true, StatusCode.OK, "删除成功");
            }
        }
        return new Result(false, StatusCode.ACCESS_ERROR, "您无权进行操作");*/
    }

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 发送短信验证码
     *
     * @return
     */
    @PostMapping("/sendsms/{mobile}")
    public Result sendCheckcode(@PathVariable String mobile) {
        //向rabbitmq消息中间件发送消息（发送短信相关信息）
        //生成验证码
        String checkcode = RandomStringUtils.randomNumeric(4);
        System.out.println("验证码："+checkcode);

        Map<String, String> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("templatecode", "SMS_119087143");  //具体查询申请好的模板code
        Map mapParam = new HashMap();
        mapParam.put("code", checkcode);
        //将短信模板参数转为json格式
        final String paramJson = JSONObject.toJSONString(mapParam);
        map.put("tempateparam", paramJson);  //发送短信参数：格式 对象形式json

        //直接模式-向指定某个队列中发送消息
        rabbitTemplate.convertAndSend("tensquare-sms", map);

        //将正确验证码存redis 设置有效期：5分钟
        redisTemplate.opsForValue().set("checkcode_"+mobile, checkcode, 5, TimeUnit.MINUTES);

        return new Result(true, StatusCode.OK, "发送短信验证码成功");
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @PostMapping("/register/{code}")
    public Result register(@RequestBody User user, @PathVariable String code){
        //判断验证码是否正确
        String realCheckcode = (String) redisTemplate.opsForValue().get("checkcode_" + user.getMobile());
        if(StringUtils.isNotBlank(realCheckcode)){
            if(realCheckcode.equals(code)){
                //验证码正确  //进行注册
                userService.register(user);
                return new Result(true, StatusCode.OK, "注册成功");
            }
        }else{
            //激活码失效
            return new Result(false, StatusCode.ERROR, "手机验证码失效");
        }
        return new Result(false, StatusCode.ERROR, "注册失败");
    }

    @Autowired
    private JwtUtil jwtUtil;
    /**
     * 用户登陆
     * @param user
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        User loginUser = userService.login(user.getMobile(), user.getPassword());
        if(loginUser!=null){
            //登陆成功&签发token
            String token = jwtUtil.createJwt(loginUser.getId(), loginUser.getNickname(), "user");
            Map<String, String> map = new HashMap<>();
            map.put("loginname", loginUser.getNickname());
            map.put("token", token);
            return new Result(true, StatusCode.OK, "登陆成功！", map);
        }
        return new Result(false, StatusCode.LOGIN_ERROR, "用户名或者密码错误！");
    }

    /**
     * 变更粉丝数
     * @param userid 用户id
     * @param x 数量 1     -1
     */
    @PostMapping("/incfans/{userid}/{x}")
    public void incFanscount(@PathVariable String userid,@PathVariable int x){
        userService.incFanscount(userid, x);
    }
    /**
     * 变更关注数
     * @param userid 用户id
     * @param x 数量 1     -1
     */
    @PostMapping("/incfollow/{userid}/{x}")
    public void incFollowcount(@PathVariable String userid,@PathVariable int x){
        userService.incFollowcount(userid, x);
    }
}
