package com.miaoshaproject.service;

import com.miaoshaproject.service.model.PromoModel;

public interface PromoService {

    //根据itemId获取即将进行的或者正在进行的秒杀活动模型
    PromoModel getPromoByItemId(Integer itemId);

}
