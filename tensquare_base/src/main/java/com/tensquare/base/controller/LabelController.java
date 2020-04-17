package com.tensquare.base.controller;

import com.tensquare.base.domain.Label;
import com.tensquare.base.service.LabelService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @program: tensquare
 * @description:
 **/
@RestController
@RequestMapping("/label")
public class LabelController {

    @Autowired
    private LabelService labelService;

    /**
     * 标签新增
     */
//    @RequestMapping(value = "", method = RequestMethod.POST)
    @PostMapping("")
    public Result save(@RequestBody Label label){
        labelService.save(label);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /**
     * 查询单个标签
     * @return
     */
    @GetMapping("/{labelId}")
    public Result findOne(@PathVariable("labelId") String labelIdx){
        int i = 1/0;
        Label label = labelService.findOne(labelIdx);
        return new Result(true, StatusCode.OK, "查询成功", label);
    }
    /**
     * 查询所有标签
     * @return
     */
    @GetMapping("")
    public Result findAll(){
        List<Label> labels = labelService.findAll();
        return new Result(true, StatusCode.OK, "查询成功", labels);
    }

    @PutMapping("/{labelId}")
    public Result update(@PathVariable String labelId, @RequestBody Label label){
        label.setId(labelId);
        labelService.update(label);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    @DeleteMapping("/{labelId}")
    public Result delete(@PathVariable String labelId){
        labelService.delete(labelId);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * 标签条件查询
     * @return
     */
    @PostMapping("/search")
    public Result search(@RequestBody Map map){
        List<Label> list = labelService.search(map);
        return new Result(true, StatusCode.OK, "查询成功", list);
    }

    /**
     * 条件分页查询
     * @param page 当前页
     * @param size 每页显示记录数
     * @param map  查询条件
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result search(@PathVariable int page, @PathVariable int size, @RequestBody Map map){
        //Pageable封装当前页，页大小
        Pageable pageable = PageRequest.of(page-1, size);
       //Page对象中查询到总记录数，当前页记录
        Page pageData = labelService.search(map, pageable);
        System.out.println(pageData.getTotalElements());
        System.out.println(pageData.getContent());
        //如果直接将Page对象转为json返回，不符合api要求返回格式
        PageResult<Label> pageResult = new PageResult<>(pageData.getTotalElements(), pageData.getContent());
        return new Result(true, StatusCode.OK, "查询成功", pageResult);
    }
}
