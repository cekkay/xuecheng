package com.xuecheng.base.exception;

import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

/**
 * @author shi
 * @Description 和前端约定返回的异常信息模型
 * @create 2023-05-2023/5/9-8:49
 */


public class RestErrorResponse implements Serializable {

    private String errMessage;

    public RestErrorResponse(String errMessage) {
        this.errMessage = errMessage;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }
}
