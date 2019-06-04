package com.l.demo.service.impl;

import com.l.demo.mapper.VideoMapper;
import com.l.demo.model.StoryCat;
import com.l.demo.model.Video;
import com.l.demo.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by luocheng on 2019/3/11.
 */
@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoMapper videoMapper;

  
    @Override
    public void insert(Video modal) {
        videoMapper.insert(modal);
    }



    @Override
    public List<Video> queryData() {
        return videoMapper.selectData();
    }

    @Override
    public void delById(Integer id) {
        //假删
        Video modal = videoMapper.selectByPrimaryKey(id);
        modal.setIsDel(1);
        videoMapper.updateByPrimaryKey(modal);
    }
}
