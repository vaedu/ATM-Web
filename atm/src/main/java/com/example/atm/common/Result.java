package com.example.atm.common;

import lombok.Data;

@Data
public class Result<T> {
    private int code;   // 状态码：200成功，400失败
    private String msg; // 描述信息
    private T data;     // 返回数据

    // 成功
    public static <T> Result<T> success(T data) {
        Result<T> r = new Result<>();
        r.setCode(200);
        r.setMsg("success");
        r.setData(data);
        return r;
    }

    // 失败
    public static <T> Result<T> error(String msg) {
        Result<T> r = new Result<>();
        r.setCode(400);
        r.setMsg(msg);
        r.setData(null);
        return r;
    }
}
