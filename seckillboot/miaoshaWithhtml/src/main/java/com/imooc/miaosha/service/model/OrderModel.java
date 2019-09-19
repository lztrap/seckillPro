package com.imooc.miaosha.service.model;

import java.math.BigDecimal;

/**
 * @ClassName OrderModel 用户下单交易模型
 * Created by zghgchao 2019/3/10 20:53
 **/
public class OrderModel {
    // 20190310000012
    private String id;

    private Integer userId;

    private Integer itemId;

    // 若非空，则表示以秒杀商品方式下单
    private Integer promoId;

    // 购买数量
    private Integer amount;

    // 购买商品的单价，若promoId非空，则表示秒杀商品价格
    private BigDecimal itemPrice;

    // 购买金额
    private BigDecimal orderAccount;

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

    public BigDecimal getOrderAccount() {
        return orderAccount;
    }

    public void setOrderAccount(BigDecimal orderAccount) {
        this.orderAccount = orderAccount;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Integer getPromoId() {
        return promoId;
    }

    public void setPromoId(Integer promoId) {
        this.promoId = promoId;
    }
}
