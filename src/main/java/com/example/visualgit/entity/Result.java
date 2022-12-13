package com.example.visualgit.entity;

import lombok.Data;

import java.util.Map;

@Data
public class Result {
    private int code;
    private String message;
    private boolean state;
    private Map<String,Object> data;

    private Result(){}

    public static Result ok(){
        Result result=new Result();
        result.state = true;
        return result;
    }

    public static Result error(){
        Result result=new Result();
        result.state = false;
        return result;
    }

    public Result code(int code){
        this.code = code;
        return this;
    }

    public Result message(String message){
        this.message=message;
        return this;
    }

    public Result data(Map<String, Object> data){
        this.data = data;
        return this;
    }
}
