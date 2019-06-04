package com.l.demo.mapper;

import com.l.demo.model.PhotoAlbum;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PhotoAlbumMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PhotoAlbum record);

    int insertSelective(PhotoAlbum record);

    PhotoAlbum selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PhotoAlbum record);

    int updateByPrimaryKey(PhotoAlbum record);

    List<PhotoAlbum> selectAlbumList();
}