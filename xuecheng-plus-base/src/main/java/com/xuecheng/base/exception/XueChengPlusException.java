package com.xuecheng.base.exception;

/**
 * @author shi
 * @Description 本项目自定义异常类型
 * @create 2023-05-2023/5/9-8:54
 */


public class XueChengPlusException extends RuntimeException{

    private String errMessage;

    public XueChengPlusException() {
    }

    public XueChengPlusException(String message) {
        super(message);
        this.errMessage = message;
    }

    public static void cast(String message) {
        throw new XueChengPlusException(message);
    }

    public static void cast(CommonError commonError) {
        throw new XueChengPlusException(commonError.getErrMessage());
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }
}
