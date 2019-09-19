package com.miaoshaproject.service;

import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.service.model.OrderModel;
import org.omg.PortableInterceptor.INACTIVE;

public interface OrderService {

    //使用 1.订单创建  通过前端url传过来秒杀活动id 然后下单接口内校验对应id是否属于对应商品且活动已经开始
    //2.直接在下单接口内判断对应的商品是否有秒杀活动，若存在进行中的秒杀活动则以秒杀活动价格下单
    OrderModel createOrder(Integer userId, Integer itemId, Integer promoId, Integer amount) throws BusinessException;



}
