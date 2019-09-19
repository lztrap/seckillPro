package com.miaoshaproject.service.impl;

import com.miaoshaproject.dao.PromoDOMapper;
import com.miaoshaproject.dataobject.PromoDO;
import com.miaoshaproject.service.PromoService;
import com.miaoshaproject.service.model.PromoModel;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromoServiceImpl implements PromoService {

    @Autowired
    private PromoDOMapper promoDOMapper;

    @Override
    public PromoModel getPromoByItemId(Integer itemId) {

        //获取对应商品的秒杀活动信息
        PromoDO promoDO = promoDOMapper.selectByItemId(itemId);

        //dataobject - model
        PromoModel promoModel = this.convertFromDataObject(promoDO);

        if (promoModel == null){
            return null;
        }

        //判断当前活动是否正在进行
        DateTime now = new DateTime();
        if(promoModel.getStartDate().isAfterNow())
            promoModel.setStatus(1);
        else if(promoModel.getEndDate().isBeforeNow())
            promoModel.setStatus(3);
        else
            promoModel.setStatus(2);

        return promoModel;
    }

    private PromoModel convertFromDataObject(PromoDO promoDO){

        if(promoDO == null)
            return null;

        PromoModel promoModel = new PromoModel();
        BeanUtils.copyProperties(promoDO, promoModel);
        promoModel.setPromoItemPrice(promoDO.getPromoItemPrice());
        promoModel.setStartDate(new DateTime(promoDO.getStartDate()));
        promoModel.setEndDate(new DateTime(promoDO.getEndDate()));

        return promoModel;
    }
}
