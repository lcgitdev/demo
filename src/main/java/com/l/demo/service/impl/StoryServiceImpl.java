package com.l.demo.service.impl;

import com.l.demo.mapper.BbsMapper;
import com.l.demo.mapper.StoryCatMapper;
import com.l.demo.mapper.StorysMapper;
import com.l.demo.model.Bbs;
import com.l.demo.model.StoryCat;
import com.l.demo.model.Storys;
import com.l.demo.service.BbsService;
import com.l.demo.service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by luocheng on 2019/3/11.
 */
@Service
public class StoryServiceImpl implements StoryService {

    @Autowired
    private StorysMapper storysMapper;

    @Autowired
    private StoryCatMapper storyCatMapper;

    @Override
    public void insert(Storys modal) {
        storysMapper.insert(modal);
    }

    @Override
    public void insertStoryCat(StoryCat model) {
        storyCatMapper.insert(model);
    }

    @Override
    public List<Storys> queryData(Integer type) {
        return storysMapper.selectData(type);
    }

    @Override
    public List<StoryCat> queryCatData() {
        return storyCatMapper.selectData();
    }

    @Override
    public Integer getCountByType(Integer type) {
        return storysMapper.getCountByType(type);
    }

    @Override
    public void delById(Integer id) {
        //假删
        Storys modal = storysMapper.selectByPrimaryKey(id);
        modal.setIsDel(1);
        storysMapper.updateByPrimaryKey(modal);
    }
}
