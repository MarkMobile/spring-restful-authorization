package com.scienjus.config;

/**
 * 常量
 * @author ScienJus
 * @date 2015/7/31.
 */
public class Constants {

    /**
     * 存储当前登录用户id的字段名
     */
    public static final String CURRENT_USER_ID = "CURRENT_USER_ID";

    /**
     * token有效期（小时）
     */
    public static final int TOKEN_EXPIRES_HOUR = 30*1*60*60;//天时分秒
    
    public static final int TOKEN_EXPIRES_SECONDS = 7*24*60*60*1000;//天时分秒

    /**
     * 存放Authorization的header字段
     */
    public static final String AUTHORIZATION = "authorization";

}
