package com.miaoshaproject.dao;

import com.miaoshaproject.dataobject.PromoDO;
import com.miaoshaproject.dataobject.PromoDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PromoDOMapper {
    long countByExample(PromoDOExample example);

    int deleteByExample(PromoDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PromoDO record);

    int insertSelective(PromoDO record);

    List<PromoDO> selectByExample(PromoDOExample example);

    PromoDO selectByPrimaryKey(Integer id);

    PromoDO selectByItemId(Integer itemId);

    int updateByExampleSelective(@Param("record") PromoDO record, @Param("example") PromoDOExample example);

    int updateByExample(@Param("record") PromoDO record, @Param("example") PromoDOExample example);

    int updateByPrimaryKeySelective(PromoDO record);

    int updateByPrimaryKey(PromoDO record);
}