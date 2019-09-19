package com.miaoshaproject.dao;

import com.miaoshaproject.dataobject.ItemStockDO;
import com.miaoshaproject.dataobject.ItemStockDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ItemStockDOMapper {
    long countByExample(ItemStockDOExample example);

    int deleteByExample(ItemStockDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ItemStockDO record);

    int insertSelective(ItemStockDO record);

    List<ItemStockDO> selectByExample(ItemStockDOExample example);

    ItemStockDO selectByPrimaryKey(Integer id);

    ItemStockDO selectByItemId(Integer itemId);

    int updateByExampleSelective(@Param("record") ItemStockDO record, @Param("example") ItemStockDOExample example);

    int updateByExample(@Param("record") ItemStockDO record, @Param("example") ItemStockDOExample example);

    int updateByPrimaryKeySelective(ItemStockDO record);

    int updateByPrimaryKey(ItemStockDO record);

    int decreaseStock(@Param("itemId") Integer itemId, @Param("amount") Integer amount);
}