package com.tensquare.user.interceptors;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: tensquare_parent
 * @description: 拦截前端请求，获取请求头信息 token进行转发
 **/
@Component
public class JwtInterceptor extends HandlerInterceptorAdapter{

    @Autowired
    private JwtUtil jwtUtil;
    /**
     * 解析token 返回claims信息
     * @param request
     * @param response
     * @param handler
     * @return true:放行  false:拦截
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String header = request.getHeader("Authorization");
        if(header!=null && header.startsWith("Bearer ")){
            String token = header.substring(7);
            //解析token
            Claims claims = jwtUtil.parseJwt(token);
            String role = (String) claims.get("role");
            if("admin".equals(role)){
                request.setAttribute("admin_claims", claims);
            }
            if("user".equals(role)){
                request.setAttribute("user_claims", claims);
            }
        }
        return true;
    }
}
