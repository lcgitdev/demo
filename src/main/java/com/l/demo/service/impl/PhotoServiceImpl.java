package com.l.demo.service.impl;

import com.l.demo.mapper.PhotoAlbumMapper;
import com.l.demo.mapper.PhotosMapper;
import com.l.demo.model.PhotoAlbum;
import com.l.demo.model.Photos;
import com.l.demo.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by luocheng on 2019/3/11.
 */
@Service
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    private PhotoAlbumMapper photoAlbumMapper;
    @Autowired
    private PhotosMapper photosMapper;

    @Override
    public void insertAlbum(PhotoAlbum modal) {
        photoAlbumMapper.insert(modal);
    }

    @Override
    public void delAlbumById(Integer id) {
        //假删
        PhotoAlbum modal = photoAlbumMapper.selectByPrimaryKey(id);
        modal.setIsDel(1);
        photoAlbumMapper.updateByPrimaryKey(modal);
    }

    @Override
    public List<PhotoAlbum> getAlbumList() {
        List<PhotoAlbum> list =  photoAlbumMapper.selectAlbumList();
        return list;
    }

    @Override
    public List<Photos> getPhotosByAlbumId(Integer id) {
        List<Photos> list =  photosMapper.getPhotosByAlbumId(id);
        return list;
    }

    @Override
    public void addPhoto(Photos photos) {
        photosMapper.insert(photos);
    }
}
