package com.miaoshaproject.service.impl;

import com.miaoshaproject.dao.UserDOMapper;
import com.miaoshaproject.dao.UserPasswordDOMapper;
import com.miaoshaproject.dataobject.UserDO;
import com.miaoshaproject.dataobject.UserPasswordDO;
import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.service.UserService;
import com.miaoshaproject.service.model.UserModel;
import com.miaoshaproject.validator.ValidationResult;
import com.miaoshaproject.validator.ValidatorImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDOMapper userDOMapper;
    @Autowired
    private UserPasswordDOMapper userPasswordDOMapper;
    @Autowired
    private ValidatorImpl validator;

    @Override
    public UserModel getUserById(Integer id) {

        //调用dao层处理
        UserDO userDO = userDOMapper.selectByPrimaryKey(id);

        if(userDO == null)
            return null;

        //通过用户id获取获取用户加密密码信息
        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(id);

        return convertFromDataObject(userDO, userPasswordDO);
    }

    //用户注册
    @Override
    @Transactional
    public void register(UserModel userModel) throws BusinessException{

        if(userModel == null)
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);

/*        if(StringUtils.isEmpty(userModel.getName())
                ||userModel.getGender()==null
                ||userModel.getAge()==null
                ||StringUtils.isEmpty(userModel.getTelphone())){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }*/

        // 参数校验
        ValidationResult result = validator.validate(userModel);
        if (result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }

        //实现model-dataobject
        UserDO userDO = convertFromModel(userModel);
        //插入用户数据库
        userDOMapper.insertSelective(userDO);
        //设置用户id
        userModel.setId(userDO.getId());

        //实现model-dataobject
        UserPasswordDO userPasswordDO = convertPasswordFromModel(userModel);
        //插入密码数据库
        userPasswordDOMapper.insertSelective(userPasswordDO);

    }

    //用户登录
    @Override
    public UserModel validateLogin (String telphone, String encrptPassword) throws BusinessException{

        //通过用户手机号获取用户信息
        UserDO userDO = userDOMapper.selectByTelphone(telphone);

        if(userDO == null)
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);

        UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());

        UserModel userModel = convertFromDataObject(userDO, userPasswordDO);

        //校验密码是否正确
        if(! StringUtils.equals(encrptPassword, userModel.getEncrptPassword()) ){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        return userModel;
    }


    /***********************  model和dataObject的转换  ******************/

    //转换UserModel到UserDO
    private UserDO convertFromModel(UserModel userModel){
        if(userModel == null)
            return null;

        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userModel, userDO);

        return userDO;
    }

    //转换UserModel到UserPasswordDO
    private UserPasswordDO convertPasswordFromModel(UserModel userModel){

        if(userModel == null)
            return null;


        UserPasswordDO userPasswordDO = new UserPasswordDO();
        userPasswordDO.setEncrptPassword(userModel.getEncrptPassword());
        userPasswordDO.setUserId(userModel.getId());

        return userPasswordDO;
    }

    //转换UserDO UserPasswordDO到UserModel
    private UserModel convertFromDataObject(UserDO userDO, UserPasswordDO userPasswordDO){

        if(userDO == null)
            return null;

        UserModel userModel = new UserModel();

        //拷贝
        BeanUtils.copyProperties(userDO, userModel);

        if(userPasswordDO != null)
            userModel.setEncrptPassword(userPasswordDO.getEncrptPassword());

        return userModel;
    }
}
