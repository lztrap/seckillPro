package com.imooc.miaosha.error;

/**
 * @ClassName CommonError
 * Created by zghgchao 2019/3/7 15:05
 **/
public interface CommonError {

    public int getErrCode();
    public String getErrMsg();
    public CommonError setErrMsg(String errMsg);

}
