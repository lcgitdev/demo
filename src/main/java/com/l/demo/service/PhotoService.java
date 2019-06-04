package com.l.demo.service;

import com.l.demo.model.PhotoAlbum;
import com.l.demo.model.Photos;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by luocheng on 2019/3/11.
 */
public interface PhotoService {

    public void insertAlbum(PhotoAlbum modal);

    List<PhotoAlbum> getAlbumList();

    List<Photos> getPhotosByAlbumId(Integer id);

    void addPhoto(Photos photos);

    void delAlbumById(Integer id);
}
