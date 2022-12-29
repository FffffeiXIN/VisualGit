package com.example.visualgit.handler;

import com.example.visualgit.entity.Result;
import com.example.visualgit.exception.DataBaseException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataBaseException.class)
    public Result DatabaseException() {
        return Result.error().code(500).message("数据库操作错误");
    }

    @ExceptionHandler(Exception.class)
    public Result UnknownException() {
        return Result.error().code(500).message("未知错误");
    }
}
