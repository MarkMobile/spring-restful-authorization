package com.scienjus.controller;

import com.scienjus.authorization.annotation.Authorization;
import com.scienjus.authorization.annotation.CurrentUser;
import com.scienjus.authorization.manager.TokenManager;
import com.scienjus.authorization.model.TokenModel;
import com.scienjus.config.ResultStatus;
import com.scienjus.domain.User;
import com.scienjus.model.ResultModel;
import com.scienjus.repository.UserRepository;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;

import org.apache.tomcat.util.buf.UDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 获取和删除token的请求地址，在Restful设计中其实就对应着登录和退出登录的资源映射
 * @author ScienJus
 * @date 2015/7/30.
 */
@RestController
@RequestMapping("/tokens")
public class TokenController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenManager tokenManager;

    @RequestMapping(method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    @ApiOperation(value = "登录")
    public ResponseEntity<ResultModel> login(@RequestParam String username, @RequestParam String password) {
        Assert.notNull(username, "username can not be empty");
        Assert.notNull(password, "password can not be empty");
        System.out.println("用户名："+username+" 密码："+password+" 转码："+UDecoder.URLDecode(username));
        User user = userRepository.findByUsername(username);
        if (user == null ||  //未注册
                !user.getPassword().equals(password)) {  //密码错误
            //提示用户名或密码错误
            return new ResponseEntity<>(ResultModel.error(ResultStatus.USERNAME_OR_PASSWORD_ERROR), HttpStatus.NOT_FOUND);
        }
        //生成一个token，保存用户登录状态
        TokenModel model = tokenManager.createToken(user.getId());
        return new ResponseEntity<>(ResultModel.ok(model), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @Authorization
    @ApiOperation(value = "退出登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),
    })
    public ResponseEntity<ResultModel> logout(@CurrentUser User user) {
        tokenManager.deleteToken(user.getId());
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    @Authorization
    @ApiOperation(value = "获取用户信息")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "authorization", value = "authorization", required = true, dataType = "string", paramType = "header"),
   })
    public ResponseEntity<User> getUserInfo(@CurrentUser User user){
        User userModel = userRepository.findById(user.getId());
    	return new ResponseEntity<>(userModel, HttpStatus.OK);
    }
     
    @RequestMapping(method = RequestMethod.GET,value="/user/{user_id}/{id}")
    public ResponseEntity<User> getUser(@PathVariable int user_id,@PathVariable Long id){
        User userModel = userRepository.findById(user_id);
        System.out.println("id="+id);
    	return new ResponseEntity<>(userModel, HttpStatus.OK);
    }
    
    @ApiOperation(value = "获取Token值")
    @RequestMapping(method = RequestMethod.POST,value="/{user_id}")
     public ResponseEntity<ResultModel> getTokens(@PathVariable int user_id){
    	return new ResponseEntity<>(ResultModel.ok(tokenManager.getToken(user_id)), HttpStatus.OK);
   }
    
    @ApiOperation(value = "设置Token的缓存时间")
    @RequestMapping(method = RequestMethod.GET,value="/setTokens/{user_id}")
     public ResponseEntity<ResultModel> setTokens(@PathVariable int user_id,@RequestParam String time){
    	if(tokenManager.getToken(user_id).get("token")!=null){
    		tokenManager.setTokenExpire(user_id, time);
    		return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    	}else{
    		return new ResponseEntity<>(ResultModel.error(ResultStatus.TOKEN_OUT_TIME), HttpStatus.OK);
    	}
    	
   }
}
