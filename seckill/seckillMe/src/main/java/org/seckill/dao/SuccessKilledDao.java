package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.SuccessKilled;

/**
 *
 * 封装秒杀成功的单子的行为，具体实现由mapper/SuccessKilledDao.xml实现
 *
 * 向秒杀成功的表中，添加记录，商品id，手机号
 * int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);
 *
 * 根据id和手机号查询秒杀成功的商品的详情
 * SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);
 *
 * Created by zhangyijun on 15/10/5.
 */
public interface SuccessKilledDao {

    /**
     * 插入购买明细,可过滤重复
     * @param seckillId
     * @param userPhone
     * @return
     * 插入的行数
     */
    int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);

    /**
     * 根据id查询SuccessKilled并携带秒杀产品对象实体
     * @param seckillId
     * @return
     */
    SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);

}
