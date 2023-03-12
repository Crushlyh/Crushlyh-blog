package com.lyh.domain;

import com.lyh.domain.enums.AppHttpCodeEnum;

import java.io.Serializable;

/**
 * Author:crushlyh
 * Date:2023/2/19 20:49
 */
public class ResponseResult<T> implements Serializable {
    private Integer code;
    private String msg;
    private T data;
    public ResponseResult(){
        this.code=AppHttpCodeEnum.SUCCESS.getCode();
        this.msg= AppHttpCodeEnum.SUCCESS.getMsg();
    }

    public ResponseResult(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public ResponseResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private ResponseResult error(int code, String msg) {
        this.code=code;
        this.msg=msg;
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ResponseResult ok(int code, T data, String msg) {
        this.code=code;
        this.data=data;
        this.msg=msg;
        return this;
    }
    public ResponseResult ok(int code, String msg) {
        this.code=code;
        this.msg=msg;
        return this;
    }
    public ResponseResult ok(int code, T data) {
        this.code=code;
        this.data=data;
        return this;
    }
    public ResponseResult ok(T data) {
        this.data=data;
        return this;
    }

    public static ResponseResult errorResult(int code,String msg){
        ResponseResult result=new ResponseResult();
        return result.error(code,msg);
    }
    public static ResponseResult errorResult(AppHttpCodeEnum Enums){
        return setApphttpCodeEnum(Enums);
    }
    public static ResponseResult errorResult(AppHttpCodeEnum Enums,String msg){
        return setApphttpCodeEnum(Enums,msg);
    }
    public static ResponseResult setApphttpCodeEnum(AppHttpCodeEnum enums){
        return okResult(enums.getCode(),enums.getMsg());
    }
    public static ResponseResult setApphttpCodeEnum(AppHttpCodeEnum enums,String msg){
        return okResult(enums.getCode(),msg);
    }

    public static ResponseResult okResult(){
        ResponseResult result=new ResponseResult();
        return  result;
    }
    public static ResponseResult okResult(int code,String msg){
        ResponseResult result=new ResponseResult();
        return  result.ok(code,null,msg);
    }
    public static ResponseResult okResult(Object data){
        ResponseResult result=setApphttpCodeEnum(AppHttpCodeEnum.SUCCESS);//?
        if(data!=null){
            result.ok(data);
        }
        return  result;
    }



}
