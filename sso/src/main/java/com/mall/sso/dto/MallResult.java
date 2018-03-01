package com.mall.sso.dto;

/**
 * @description: 传输对象
 * @author: Jingzeng Wang
 * @date: created in 2017/10/22 18:47
 * @modified By:
 */
public class MallResult {
    /**
     * http状态码
     */
    private Integer status;
    /**
     * 提示消息
     */
    private String msg;
    /**
     * 可传递的数据
     */
    private String data;

    public MallResult(Integer status, String msg, String data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public static MallResult ok() {
        return new MallResult(200, null, null);
    }

    public static MallResult badRequest() {
        return new MallResult(400, null, null);
    }

    public static MallResult internalError() {
        return new MallResult(500, null, null);
    }

    public static MallResult build(Integer status, String msg) {
        return new MallResult(status, msg, null);
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
