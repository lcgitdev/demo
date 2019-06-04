package com.l.demo.service;

import com.l.demo.model.StoryCat;
import com.l.demo.model.Video;

import java.util.List;

/**
 * Created by luocheng on 2019/3/11.
 */
public interface VideoService {

    public void insert(Video modal);

    List<Video> queryData();

    void delById(Integer id);


}
