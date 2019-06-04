package com.l.demo.service.impl;

import com.l.demo.mapper.BbsMapper;
import com.l.demo.mapper.PhotoAlbumMapper;
import com.l.demo.mapper.PhotosMapper;
import com.l.demo.model.Bbs;
import com.l.demo.model.PhotoAlbum;
import com.l.demo.model.Photos;
import com.l.demo.service.BbsService;
import com.l.demo.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by luocheng on 2019/3/11.
 */
@Service
public class BbsServiceImpl implements BbsService {

    @Autowired
    private BbsMapper bbsMapper;

    @Override
    public synchronized int inserBbs(Bbs modal) {
        return bbsMapper.insert(modal);
    }

    @Override
    public List<Bbs> queryBbsData(String nowDate) {
        return bbsMapper.selectData(nowDate);
    }
}
