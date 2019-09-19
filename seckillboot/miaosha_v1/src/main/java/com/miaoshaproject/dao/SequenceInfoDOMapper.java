package com.miaoshaproject.dao;

import com.miaoshaproject.dataobject.SequenceInfoDO;
import com.miaoshaproject.dataobject.SequenceInfoDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SequenceInfoDOMapper {
    long countByExample(SequenceInfoDOExample example);

    int deleteByExample(SequenceInfoDOExample example);

    int deleteByPrimaryKey(String name);

    int insert(SequenceInfoDO record);

    int insertSelective(SequenceInfoDO record);

    List<SequenceInfoDO> selectByExample(SequenceInfoDOExample example);

    SequenceInfoDO selectByPrimaryKey(String name);

    SequenceInfoDO getSequenceByName(String name);

    int updateByExampleSelective(@Param("record") SequenceInfoDO record, @Param("example") SequenceInfoDOExample example);

    int updateByExample(@Param("record") SequenceInfoDO record, @Param("example") SequenceInfoDOExample example);

    int updateByPrimaryKeySelective(SequenceInfoDO record);

    int updateByPrimaryKey(SequenceInfoDO record);
}