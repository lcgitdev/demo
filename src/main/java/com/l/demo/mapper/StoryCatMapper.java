package com.l.demo.mapper;

import com.l.demo.model.StoryCat;

import java.util.List;

public interface StoryCatMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StoryCat record);

    int insertSelective(StoryCat record);

    StoryCat selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StoryCat record);

    int updateByPrimaryKey(StoryCat record);

    List<StoryCat> selectData();
}