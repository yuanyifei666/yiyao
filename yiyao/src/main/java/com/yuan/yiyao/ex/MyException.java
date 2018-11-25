package com.yuan.yiyao.ex;

/**
 * 自定义异常类
 */

public class MyException  extends  Exception{

    private String messager;

    public MyException(String message) {
        super(message);
        this.messager = message;
    }

    public String getMessager() {
        return messager;
    }

    public void setMessager(String messager) {
        this.messager = messager;
    }
}
