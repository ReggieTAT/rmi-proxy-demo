package com.Reggie.bean;

import java.io.Serializable;

/**
 * 封装了各种需要的返回值,以及方法执行异常情况下向客户端发送的异常消息等
 */

public class Result implements Serializable{
    private boolean booleanFlag;//标记方法返回的布尔值
    private int intFlag;//标记方法返回的int值
    private String message;//传递异常说明等消息

    public Result() {
    }

    public boolean getBooleanFlag() {
        return booleanFlag;
    }

    public void setBooleanFlag(boolean booleanFlag) {
        this.booleanFlag = booleanFlag;
    }

    public int getIntFlag() {
        return intFlag;
    }

    public void setIntFlag(int intFlag) {
        this.intFlag = intFlag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
