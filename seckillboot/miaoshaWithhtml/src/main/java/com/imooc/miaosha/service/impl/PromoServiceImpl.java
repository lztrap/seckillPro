package com.imooc.miaosha.service.impl;

import com.imooc.miaosha.dao.PromoDOMapper;
import com.imooc.miaosha.dataobject.PromoDO;
import com.imooc.miaosha.service.PromoService;
import com.imooc.miaosha.service.model.PromoModel;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName PromoServiceImpl
 * Created by zghgchao 2019/3/11 10:22
 **/
@Service
public class PromoServiceImpl implements PromoService {

    @Autowired
    private PromoDOMapper promoDOMapper;

    @Override
    public PromoModel getPromoByItemId(Integer itemId) {
        PromoDO promoDO = promoDOMapper.selectByItemId(itemId);

        // dataobject -> model
        PromoModel promoModel = this.convertFromDataObject(promoDO);
        if (promoModel == null){
            return null;
        }

        // 判断秒杀活动是否即将开始或正在进行
        DateTime now = new DateTime();
        if (promoModel.getStartDate().isAfterNow()){
            promoModel.setStatus(1); // 还未开始
        }else if (promoModel.getEndDate().isBeforeNow()){
            promoModel.setStatus(3); // 还未结束
        }else {
            promoModel.setStatus(2);
        }

        return promoModel;
    }

    private PromoModel convertFromDataObject(PromoDO promoDO){
        if (promoDO == null){
            return null;
        }
        PromoModel promoModel = new PromoModel();
        BeanUtils.copyProperties(promoDO,promoModel);
        promoModel.setStartDate(new DateTime(promoDO.getStartDate()));
        promoModel.setEndDate(new DateTime(promoDO.getEndDate()));
        return promoModel;
    }
}
