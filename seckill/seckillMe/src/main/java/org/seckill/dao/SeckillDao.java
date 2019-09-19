package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.Seckill;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 封装关于商品的实体SeckillDao的行为，具体实现由mapper/SeckillDao.xml实现
 *
 * 减少商品数量
 * int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);
 *
 * 根据id查询商品
 * Seckill queryById(long seckillId);
 *
 * 根据偏移量查询商品列表
 * List<Seckill> queryAll(@Param("offset") int offet, @Param("limit") int limit);
 *
 * Created by zhangyijun on 15/10/5.
 */
public interface SeckillDao {

    /**
     * 减库存,
     * @param seckillId
     * @param killTime
     * @return 如果影响行数>1，表示更新的记录行数
     */
    int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);

    /**
     * 根据id查询秒杀对象
     * @param seckillId
     * @return
     */
    Seckill queryById(long seckillId);

    /**
     * 根据偏移量查询秒杀商品列表
     * @param offet
     * @param limit
     * @return
     */
    List<Seckill> queryAll(@Param("offset") int offet, @Param("limit") int limit);


}
