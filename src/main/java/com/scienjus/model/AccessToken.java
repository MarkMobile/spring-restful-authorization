package com.scienjus.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * SAAS Token WMS管理实体
 * 
 * @packge com.wlyd.wmscloud.persistence.beans.api.AccessToken
 * @date 2016年5月6日 上午10:27:55
 * @author pengjunlin
 * @comment
 * @update
 */
public class AccessToken implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4759692267927548118L;

	private String token;// AccessToken字符串

	private Timestamp timestamp;// 时间戳(用于验证token和重新获取token)

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	
	public long getLongTime(){
		if(timestamp!=null){
			return timestamp.getTime();
		}
		return 0;
	}

}