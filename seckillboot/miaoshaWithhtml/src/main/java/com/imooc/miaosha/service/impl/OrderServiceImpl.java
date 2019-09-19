package com.imooc.miaosha.service.impl;

import com.imooc.miaosha.dao.OrderDOMapper;
import com.imooc.miaosha.dao.SequenceInfoDOMapper;
import com.imooc.miaosha.dataobject.OrderDO;
import com.imooc.miaosha.dataobject.SequenceInfoDO;
import com.imooc.miaosha.error.BusinessException;
import com.imooc.miaosha.error.EmBusinessError;
import com.imooc.miaosha.service.ItemService;
import com.imooc.miaosha.service.OrderService;
import com.imooc.miaosha.service.UserService;
import com.imooc.miaosha.service.model.ItemModel;
import com.imooc.miaosha.service.model.OrderModel;
import com.imooc.miaosha.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @ClassName OrderServiceImpl
 * Created by zghgchao 2019/3/10 21:08
 **/
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderDOMapper orderDOMapper;

    @Autowired
    private SequenceInfoDOMapper sequenceInfoDOMapper;

    @Override
    @Transactional
    public OrderModel createOrder(Integer userId, Integer itemId, Integer promoId, Integer amount) throws BusinessException {
        // 校验下单状态，下单的商品是否存在，用户是否合法，购买数量是否正确
        ItemModel itemModel = itemService.getItemById(itemId);
        if (itemModel == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROP,"商品信息不存在");
        }
        UserModel userModel = userService.getUser(userId);
        if (userModel == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROP,"用户信息不存在");
        }
        if (amount <= 0 || amount > 99){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROP,"下单数量不正确");
        }

        // 校验活动信息
        if (promoId != null){
            // (1)校验对应活动是否存在这个适用商品
            if (promoId.intValue() != itemModel.getPromoModel().getId()){
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROP,"秒杀活动信息不正确");
            }else if (itemModel.getPromoModel().getStatus().intValue() != 2){
                // (2)校验活动是否正在进行中
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROP,"秒杀活动还未开始");
            }
        }

        // 落单减库存/支付减库存
        boolean result = itemService.decreaseStock(itemId, amount);
        if (!result){
            throw new BusinessException(EmBusinessError.STOCK_NOT_ENOUGH);
        }

        // 订单入库
        OrderModel orderModel = new OrderModel();
        orderModel.setUserId(userId);
        orderModel.setItemId(itemId);
        orderModel.setAmount(amount);
        if (promoId != null){
            orderModel.setPromoId(promoId);
            orderModel.setItemPrice(itemModel.getPromoModel().getPromoItemPrice());
        }else {
            orderModel.setItemPrice(itemModel.getPrice());
        }

        orderModel.setOrderAccount(orderModel.getItemPrice().multiply(new BigDecimal(amount)));

        // 生成交易流水号
        orderModel.setId(this.generateOrderNo());
        OrderDO orderDO = this.convertFromOrderModel(orderModel);
        orderDOMapper.insertSelective(orderDO);
        // 商品销量增加
        itemService.increaseSales(itemId,amount);

        // 返回前端
        return orderModel;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String generateOrderNo(){
        // 订单号有16位
        StringBuilder stringBuilder = new StringBuilder();
        // 前8位为时间信息，年月日
        LocalDateTime now = LocalDateTime.now();
        String nowDate = now.format(DateTimeFormatter.ISO_DATE).replace("-","");
        stringBuilder.append(nowDate);
        // 中间6位为自增序列
        // String idStr = String.format("%06d", sequence);
        int sequence = 0;
        SequenceInfoDO sequenceInfoDO = sequenceInfoDOMapper.getSequenceInfo("order_info");
        sequence = sequenceInfoDO.getCurrentValue();
        sequenceInfoDO.setCurrentValue(sequence + sequenceInfoDO.getStep());
        sequenceInfoDOMapper.updateByPrimaryKey(sequenceInfoDO);
        String sequenceStr = String.valueOf(sequence);
        for (int i=0;i < 6-sequenceStr.length();i++){
            stringBuilder.append("0");
        }
        stringBuilder.append(sequenceStr);

        // 最后2位为分库分表位
        stringBuilder.append("00");

        return stringBuilder.toString();
    }

    private OrderDO convertFromOrderModel(OrderModel orderModel){
        if (orderModel == null){
            throw null;
        }
        OrderDO orderDO = new OrderDO();
        BeanUtils.copyProperties(orderModel,orderDO);

        return orderDO;
    }

    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        String format = now.format(DateTimeFormatter.ISO_DATE);
        System.out.println(format.replace("-",""));
    }
}
