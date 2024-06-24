package com.yx.utils;

import java.io.Serializable;
//数据封装类，返回给前端规范化的数据
public class DataInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer code; //状态码
    private String msg; //消息
    private Object data; //json数据
    private Long count; // 分页信息的总条数

    public DataInfo() {
    }

    public DataInfo(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public DataInfo(Integer code, String msg, Object data, Long count) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.count = count;
    }

    public Integer getCode() {
        return code;
    }
    // 成功时的静态工厂方法（没有数据）
    public static DataInfo ok() {
        return new DataInfo(Constants.OK_CODE, Constants.OK_MSG, null);
    }
    // 成功时的静态工厂方法（有数据）
    public static DataInfo ok(Object data) {
        return new DataInfo(Constants.OK_CODE, Constants.OK_MSG, data);
    }
    // 成功时的静态工厂方法（带消息和分页信息）
    public static DataInfo ok(String msg, long count, Object data) {
        return new DataInfo(Constants.OK_CODE, Constants.OK_MSG, data,count);
    }
    // 成功时的静态工厂方法（带消息）
    public static DataInfo ok(String msg, Object data) {
        return new DataInfo(Constants.OK_CODE, msg, data);
    }
    // 失败时的静态工厂方法（带失败消息）
    public static DataInfo fail(String msg) {
        return new DataInfo(Constants.FAIL_CODE, msg, null);
    }
    // 失败时的静态工厂方法（带错误码和失败消息）
    public static DataInfo fail(int errorCode, String msg) {
        return new DataInfo(errorCode, msg, null);
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
