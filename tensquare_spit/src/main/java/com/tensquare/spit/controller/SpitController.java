package com.tensquare.spit.controller;

import com.tensquare.spit.domain.Spit;
import com.tensquare.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: tensquare
 * @description:
 **/
@RestController
@RequestMapping("/spit")
@CrossOrigin
public class SpitController {

    @Autowired
    private SpitService spitService;


    @PostMapping("")
    public Result save(@RequestBody Spit spit){
        spitService.save(spit);
        return new Result(true, StatusCode.OK, "吐槽成功");
    }

    @GetMapping("")
    public Result findAll(){
        List<Spit> list = spitService.findAll();
        return new Result(true, StatusCode.OK, "查询成功", list);
    }

    @GetMapping("/{spitId}")
    public Result findById(@PathVariable String spitId){
        Spit spit = spitService.findById(spitId);
        return new Result(true, StatusCode.OK, "查询成功", spit);
    }

    /**
     * 根据上级吐槽id查询吐槽数据
     * @param parentid 上级id
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/comment/{parentid}/{page}/{size}")
    public Result findByparentId(@PathVariable String parentid, @PathVariable int page, @PathVariable int size){
        Pageable pageable = PageRequest.of(page-1, size);
        Page<Spit> pageData = spitService.findByParentId(parentid, pageable);
        return new Result(true,StatusCode.OK, "查询成功", new PageResult<Spit>(pageData.getTotalElements(), pageData.getContent()));
    }

    /**
     * 吐槽点赞
     * @param spitId 吐槽id
     * @return
     */
    @PutMapping("/thumbup/{spitId}")
    public Result thumbup(@PathVariable String spitId){
        spitService.updateThumbup(spitId);
        return new Result(true,StatusCode.OK, "点赞成功");
    }
    /**
     * 增加浏览量
     */
    @PutMapping("/visits/{spitId}")
    public Result visits(@PathVariable String spitId){
        spitService.updateVisits(spitId);
        return new Result(true,StatusCode.OK, "浏览成功");
    }

    /**
     * 增加分享数
     */
    @PutMapping("/share/{spitId}")
    public Result share(@PathVariable String spitId){
        spitService.updateShare(spitId);
        return new Result(true,StatusCode.OK, "分享成功");
    }


}

