package com.scienjus.authorization.manager;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javassist.bytecode.Mnemonic;

import org.springframework.util.Base64Utils;

import com.scienjus.model.AccessToken;
import com.scienjus.utils.RSAUtils;


/**
 * SAAS Token管理工具
 * 
 * @packge com.wlyd.wmscloud.util.SAASTokenManager
 * @date 2016年5月6日 上午10:20:01
 * @author pengjunlin
 * @comment
 * @update
 */
public class SAASTokenManager {
	

	/**
	 * Token存储对象，保持5000个并发容量(K-useraccount@corCode,V-token)
	 */
	public static Map<String, Object> map = new ConcurrentHashMap<String, Object>(
			5000);

	/**
	 * 获取用户Token
	 * 
	 * @MethodName: getToken
	 * @Description:
	 * @param key
	 * @return
	 * @throws
	 */
	public static AccessToken getToken(String key) {
		if (map.containsKey(key)) {
			return (AccessToken) map.get(key);
		}
		return null;
	}

	/**
	 * 添加用户token
	 * 
	 * @MethodName: putToken
	 * @Description:
	 * @param key
	 *            useraccount@corCode
	 * @param accessToken
	 * @throws
	 */
	public static void putToken(String key, AccessToken accessToken) {
		map.put(key, accessToken);
	}

	/**
	 * 移除token
	 * 
	 * @MethodName: removeToken
	 * @Description:
	 * @param key
	 *            useraccount@corCode
	 * @throws
	 */
	public static void removeToken(String key) {
		if (map.containsKey(key)) {
			map.remove(key);
		}
	}

	/**
	 * 验证Token是否过期
	 * 
	 * @MethodName: isVlidateToken
	 * @Description:
	 * @param key
	 *            useraccount@corCode
	 * @return
	 * @throws
	 */
	public static boolean isVlidateToken(String key) {
		if (map.containsKey(key)) {
			AccessToken accessToken = (AccessToken) map.get(key);
			long currentTimestamp = new Date().getTime();
			// 有效时间两小时
			if (accessToken.getLongTime() - currentTimestamp > 2 * 3600 * 1000) {
				return false;
			}
			return true;
		}
		return false;
	}

	/**
	 * 更新Token
	 * 
	 * @MethodName: reputToken
	 * @Description:
	 * @param key
	 *            useraccount@corCode
	 * @param accessToken
	 * @return
	 * @throws
	 */
	public static void reputToken(String key, AccessToken accessToken) {
		if (map.containsKey(key)) {
			putToken(key, accessToken);
		}
	}

	/**
	 * 更新Token
	 * 
	 * @MethodName: reputToken
	 * @Description:
	 * @param key
	 *            useraccount@corCode
	 * @param tokenStr
	 * @return
	 * @throws
	 */
	public static void reputToken(String key, String tokenStr) {
		if (map.containsKey(key)) {
			AccessToken accessToken = new AccessToken();
			accessToken.setToken(tokenStr);
			accessToken.setTimestamp(new Timestamp(new Date().getTime()));
			putToken(key, accessToken);
		}
	}
	
	/**
	 * 是否包含用户token
	 * @MethodName: iscontainKey 
	 * @Description: 
	 * @param key
	 *          useraccount@corCode
	 * @return
	 * @throws
	 */
	public static boolean iscontainKey(String key){
		return map.containsKey(key);
	}
	
	/**
	 * 生成RSA加密 Token
	 * 
	 * @MethodName: generateToken 
	 * @Description: 
	 * @param platformCode
	 * @param tenantCode
	 * @return
	 * @throws
	 */
	public static String generateToken(String publicKey,String platformCode,String tenantCode){
		String str=platformCode+tenantCode+new Date().getTime();
		try {
			byte [] bytes= RSAUtils.encryptByPublicKey(str.getBytes(),publicKey);
			return new String( bytes,"utf-8");
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		return null;
	}

	/**
	 * @throws Exception 
	 * 测试函数入口
	 * 
	 * @MethodName: main
	 * @Description:
	 * @param args
	 * @throws
	 */
	public static void main(String[] args) throws Exception {
	Map<String, Object> key=	RSAUtils.genKeyPair();
	String publicKey=	RSAUtils.getPublicKey(key);
	String privateKey=	RSAUtils.getPrivateKey(key);
	  System.out.println("公钥："+publicKey);
	  System.out.println("私钥："+privateKey);
    byte [] miwen=RSAUtils.encryptByPublicKey("13266699268".getBytes(), publicKey);
	System.out.println(new String(miwen));
	System.out.println(Base64Utils.encodeToString(miwen));
	String str="hoV41eWKxkazItgjSIDx3UEwQmOO1AEQGxJ8UPkgeGaN+aOfx28OCgJBlb43hsFp48/gttoC/gyXfLWosfRAso3tzwhSCcADPVq7GAyTgDCddD7+WZPA3sFSLHiiCmWBF5QYybn9JvY+jAyWzUsxpAjsNrE3A80Ooq3P0cpKrHE=";
    System.out.println(str.getBytes());
	if (str.getBytes() ==miwen) {
		System.out.println("相等");
	}else{
		System.out.println("不相等");
	}
	System.out.println(new String(RSAUtils.decryptByPrivateKey(miwen, privateKey)));
	
//	System.out.println("加密:"+new String(RSAUtils.encryptByPublicKey("13266699268".getBytes(), publicKey),"UTF-8"));
//	System.out.println("明文:"+new String(RSAUtils.decryptByPrivateKey(RSAUtils.encryptByPublicKey("wmsadmin@10000".getBytes(), publicKey), privateKey)));
	}

	/**
	 * @throws Exception
	 * @throws UnsupportedEncodingException
	 */
	public static void testOne() throws Exception, UnsupportedEncodingException {
		//System.out.println(Md5.getMD5Str("123456"));
		String key = "wmsadmin@10000"; 
		AccessToken accessToken = new AccessToken();
		accessToken.setToken("token==xxjisifdihfifdds");
		accessToken.setTimestamp(new Timestamp(new Date().getTime()));
		putToken(key, accessToken);
		AccessToken accessToken2 = getToken(key);
		System.out.println("token:" + accessToken2.getToken());
		System.out.println("isValidate:" + isVlidateToken(key));
		//
		Map<String, Object> keyMap=RSAUtils.genKeyPair();
		String publicKey=RSAUtils.getPublicKey(keyMap);
		String privateKey=RSAUtils.getPrivateKey(keyMap);
		
		String token=generateToken(publicKey,"abcdefghijklmnopqrstuvwxyz", "10000");
		
	 
		//
		System.out.println("加密:"+new String(RSAUtils.encryptByPublicKey("13266699268".getBytes(), publicKey),"UTF-8"));
		System.out.println("明文:"+new String(RSAUtils.decryptByPrivateKey(RSAUtils.encryptByPublicKey("wmsadmin@10000".getBytes(), publicKey), privateKey)));
	}
}
