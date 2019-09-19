package com.miaoshaproject.service.impl;

import com.miaoshaproject.dao.OrderDOMapper;
import com.miaoshaproject.dao.SequenceInfoDOMapper;
import com.miaoshaproject.dataobject.OrderDO;
import com.miaoshaproject.dataobject.SequenceInfoDO;
import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.service.ItemService;
import com.miaoshaproject.service.OrderService;
import com.miaoshaproject.service.UserService;
import com.miaoshaproject.service.model.ItemModel;
import com.miaoshaproject.service.model.OrderModel;
import com.miaoshaproject.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDOMapper orderDOMapper;

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private SequenceInfoDOMapper sequenceInfoDOMapper;

    @Override
    public OrderModel createOrder(Integer userId, Integer itemId, Integer promoId, Integer amount)  throws BusinessException{

        //校验下单状态  商品是否存在 用户是否合法 购买数量是否正确 校验活动信息/ 商品是否上架下架
        ItemModel itemModel = itemService.getItemById(itemId);
        if(itemModel == null)
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "商品信息不存在");

        UserModel userModel = userService.getUserById(userId);
        if(userModel == null)
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "用户信息不存在");

        if(amount <= 0 || amount > 99)
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "数量信息不正确");

        if(promoId != null){
            //校验对应活动是否存在这个适应商品
            if(promoId.intValue() != itemModel.getPromoModel().getId())
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "活动信息不正确");

            //校验活动是否在进行
            else if(itemModel.getPromoModel().getStatus() != 2)
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "活动还未开始");

        }

        //落单减库存，支付减库存
        boolean result = itemService.decreaseStock(itemId, amount);
        if(!result)
            throw new BusinessException(EmBusinessError.STOCK_NOT_ENOUGH);


        //订单入库
        OrderModel orderModel = new OrderModel();
        orderModel.setUserId(userId);
        orderModel.setItemId(itemId);
        orderModel.setAmount(amount);
        if(promoId != null)
            orderModel.setItemPrice(itemModel.getPromoModel().getPromoItemPrice());
        else
            orderModel.setItemPrice(itemModel.getPrice());

        orderModel.setPromoId(promoId);

        orderModel.setOrderAmount(orderModel.getItemPrice().multiply(new BigDecimal(amount)));

        //生成交易流水号 订单号
        orderModel.setId(generateOrderNo());
        OrderDO orderDO = convertFromOrderModel(orderModel);
        orderDOMapper.insertSelective(orderDO);

        //增加商品销量
        itemService.increaseSales(itemId, amount);

        //返回前端
        return orderModel;

    }

    //生成订单号
    //设置隔离级别
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String generateOrderNo(){

        StringBuilder stringBuilder = new StringBuilder();

        //订单号16位
        //前8位是时间信息 年月日
        LocalDateTime now = LocalDateTime.now();
        String nowDate = now.format(DateTimeFormatter.ISO_DATE).replace("-", "");
        stringBuilder.append(nowDate);


        //中间6位是自增序列
        //获取当前sequence
        int sequence = 0;
        SequenceInfoDO sequenceInfoDO = sequenceInfoDOMapper.getSequenceByName("order_info");

        sequence = sequenceInfoDO.getCurrentValue();
        sequenceInfoDO.setCurrentValue(sequenceInfoDO.getCurrentValue() + sequenceInfoDO.getStep());
        sequenceInfoDOMapper.updateByPrimaryKeySelective(sequenceInfoDO);

        //sequence凑够6位
        String sequenceStr = String.valueOf(sequence);
        for(int i=0; i<6-sequenceStr.length(); i++)
            stringBuilder.append(0);
        stringBuilder.append(sequenceStr);

        //最后2位是分库分表 暂时写死
        stringBuilder.append("00");

        return stringBuilder.toString();
    }


    private OrderDO convertFromOrderModel(OrderModel orderModel){
        if(orderModel == null)
            return null;

        OrderDO orderDO = new OrderDO();
        BeanUtils.copyProperties(orderModel,orderDO);
        orderDO.setOrderAccount(orderModel.getOrderAmount());

        return orderDO;
    }


    public static void main(String[] args){

        LocalDateTime now = LocalDateTime.now();
        System.out.println(now.format(DateTimeFormatter.ISO_DATE).replace("-", ""));

    }
}
