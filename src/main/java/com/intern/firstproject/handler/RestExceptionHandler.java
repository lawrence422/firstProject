package com.intern.firstproject.handler;

import com.intern.firstproject.dao.pojo.JsonResult;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public JsonResult<String> exception(Exception e){

        return JsonResult.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());

    }
}
