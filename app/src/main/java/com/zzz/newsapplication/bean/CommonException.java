package com.zzz.newsapplication.bean;

/**
 * Created by oruret on 2018/1/31.
 */

public class CommonException extends Exception{
    private static final long serialVersionUID = 1L;


    public CommonException()
    {
        super();
        // TODO Auto-generated constructor stub
    }

    public CommonException(String message, Throwable cause)
    {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    public CommonException(String message)
    {
        super(message);
        // TODO Auto-generated constructor stub
    }

    public CommonException(Throwable cause)
    {
        super(cause);
        // TODO Auto-generated constructor stub
    }
}
