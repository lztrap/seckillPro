package com.miaoshaproject.service.model;

import java.math.BigDecimal;

public class OrderModel {

    //订单号 需要有意义
    private String id;

    //购买用户id
    private Integer userId;

    //购买商品单价
    private BigDecimal itemPrice;

    //购买商品id
    private Integer itemId;

    //购买数量
    private Integer amount;

    //购买价格
    private BigDecimal orderAmount;

    //若非空 表示以秒杀商品方式下单
    private Integer promoId;

    //购买金额 若promoId非空 则以秒杀商品价格下单
    private BigDecimal orderPrice;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Integer getPromoId() {
        return promoId;
    }

    public void setPromoId(Integer promoId) {
        this.promoId = promoId;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

}
