package com.imooc.miaosha.service;

import com.imooc.miaosha.controller.viewobject.UserVO;
import com.imooc.miaosha.error.BusinessException;
import com.imooc.miaosha.service.model.UserModel;

/**
 * @ClassName UserService
 * Created by zghgchao 2019/3/7 11:24
 **/
public interface UserService {

    UserModel getUser(Integer id);

    void register(UserModel userModel) throws BusinessException;

    /**
     * 用户登陆校验
     * @param telphone 用户注册手机号
     * @param encrptPassword 用户加密后的密码
     * @throws BusinessException
     */
    UserVO validateLogin(String telphone, String encrptPassword) throws BusinessException;
}
