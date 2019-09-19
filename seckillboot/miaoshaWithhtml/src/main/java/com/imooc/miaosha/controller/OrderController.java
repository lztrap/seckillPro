package com.imooc.miaosha.controller;

import com.imooc.miaosha.controller.viewobject.UserVO;
import com.imooc.miaosha.error.BusinessException;
import com.imooc.miaosha.error.EmBusinessError;
import com.imooc.miaosha.response.CommonReturnType;
import com.imooc.miaosha.service.OrderService;
import com.imooc.miaosha.service.model.OrderModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName OrderController
 * Created by zghgchao 2019/3/11 9:15
 **/
@RestController
@RequestMapping("/order")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*",origins = {"*"}) // 支持跨域
public class OrderController extends BaseController{

    @Autowired
    private OrderService orderService;

    @Autowired
    private HttpServletRequest request;

    // 封装下单请求
    @PostMapping(value = "/createorder",consumes = CONTENT_TYPE_FORMED)
    public CommonReturnType createOrder(@RequestParam(name = "itemId") Integer itemId,
                                        @RequestParam(name = "amount") Integer amount,
                                        @RequestParam(name = "promoId",required = false) Integer promoId) throws BusinessException {
        // 获取用户的登陆信息
        Boolean isLogin = (Boolean) request.getSession().getAttribute("IS_LOGIN");
        if (isLogin == null || !isLogin.booleanValue()){
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN,"用户还未登陆，不能下单");
        }
        UserVO userVO = (UserVO) request.getSession().getAttribute("LOGIN_USER");
        OrderModel orderModel = orderService.createOrder(userVO.getId(), itemId, promoId,amount);

        return CommonReturnType.create(null);
    }

}
