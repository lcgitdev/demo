package com.l.demo.mapper;

import com.l.demo.model.StoryCat;
import com.l.demo.model.Storys;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StorysMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Storys record);

    int insertSelective(Storys record);

    Storys selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Storys record);

    int updateByPrimaryKey(Storys record);

    List<Storys> selectData(Integer type);

    Integer getCountByType(Integer type);


}