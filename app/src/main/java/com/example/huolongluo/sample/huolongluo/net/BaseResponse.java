package com.example.huolongluo.sample.huolongluo.net;

import java.io.Serializable;

public class BaseResponse<T> implements Serializable
{
    private int code;

    private String msg;

    private boolean success;

    private T data;

    public T getData()
    {
        return data;
    }

    public void setData(T data)
    {
        this.data = data;
    }

    public boolean isSuccess()
    {
        return success;
    }

    public void setSuccess(boolean success)
    {
        this.success = success;
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    @Override
    public String toString()
    {
        return "BaseResponse{" + "code=" + code + ", msg='" + msg + '\'' + '}';
    }
}