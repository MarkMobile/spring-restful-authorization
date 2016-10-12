package com.scienjus.authorization.model;

import java.util.Date;

import com.scienjus.utils.DateFormatUtil;


/**
 * Token的Model类，可以增加字段提高安全性，例如时间戳、url签名
 * @author ScienJus
 * @date 2015/7/31.
 */
public class TokenModel {

    //用户id
    private long userId;

    //随机生成的uuid
    private String token;
    
    private String expires;//token失效时间
    
    private long expires_in;

    public TokenModel(long userId, String token,String millise) {
        this.userId = userId;
        this.token = token;
        this.expires=DateFormatUtil.add(new Date(), "yyyy-MM-dd HH:mm:ss", Long.valueOf(millise));
        this.expires_in=DateFormatUtil.addLong(new Date(), "yyyy-MM-dd HH:mm:ss", Long.valueOf(millise));
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

	public String getExpires() {
		return expires;
	}

	public void setExpires(String expires) {
		this.expires = expires;
	}

	public long getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(long expires_in) {
		this.expires_in = expires_in;
	}

	

    
}
