package com.l.demo.mapper;

import com.l.demo.model.Photos;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PhotosMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Photos record);

    int insertSelective(Photos record);

    Photos selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Photos record);

    int updateByPrimaryKey(Photos record);

    List<Photos> getPhotosByAlbumId(Integer albumId);
}