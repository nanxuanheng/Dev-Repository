package com.tensquare.base.controller;

import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.Pattern;

/**
 * @program: tensquare
 * @description: 全局异常处理
 **/
@ControllerAdvice
public class BaseExceptionHandler {

    /**
     * 捕获到异常以json形式返回
     * @param e
     * @return
     */
    @ExceptionHandler
    @ResponseBody
    public Result error(Exception e){

        return new Result(false, StatusCode.ERROR, "错误信息："+e.getMessage());
    }

    /**
     * 捕获到的异常以json格式返回
     * @param e
     * @return
     */
//    public  Result error0(Exception e){
//
//        return new Result(false,StatusCode.ERROR,"错误信息："+e.getMessage());
//    }


}
