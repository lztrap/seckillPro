package com.imooc.miaosha.service;

import com.imooc.miaosha.service.model.PromoModel;

/**
 * @ClassName PromoService
 * Created by zghgchao 2019/3/11 10:18
 **/
public interface PromoService {

    // 根据itemId获取即将进行的或正在进行的秒杀活动
    PromoModel getPromoByItemId(Integer itemId);
}
