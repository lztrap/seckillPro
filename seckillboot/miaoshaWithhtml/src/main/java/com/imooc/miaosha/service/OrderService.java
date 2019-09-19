package com.imooc.miaosha.service;

import com.imooc.miaosha.error.BusinessException;
import com.imooc.miaosha.service.model.OrderModel;

/**
 * @ClassName OrderService
 * Created by zghgchao 2019/3/10 21:06
 **/
public interface OrderService {

    // 使用：1.通过前端url上传过来秒杀活动的id，然后下单接口内校验对应id是否属于对应商品且活动已开始
    // 2.直接在下单接口内判断对应的商品是否存在秒杀活动，若存在进行中的则以秒杀价格下单
    OrderModel createOrder(Integer userId,Integer itemId,Integer promoId,Integer amount) throws BusinessException;
}
