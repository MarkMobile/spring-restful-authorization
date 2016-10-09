package com.scienjus.authorization.model;

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
    
    private String TOKEN_EXPIRES_HOUR;//token失效时间

    public TokenModel(long userId, String token,String TOKEN_EXPIRES_HOUR) {
        this.userId = userId;
        this.token = token;
        this.TOKEN_EXPIRES_HOUR=TOKEN_EXPIRES_HOUR;
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

	public String getTOKEN_EXPIRES_HOUR() {
		return TOKEN_EXPIRES_HOUR;
	}

	public void setTOKEN_EXPIRES_HOUR(String tOKEN_EXPIRES_HOUR) {
		TOKEN_EXPIRES_HOUR = tOKEN_EXPIRES_HOUR;
	}
    
    
}
