package com.l.demo.service;

import com.l.demo.model.Bbs;
import com.l.demo.model.StoryCat;
import com.l.demo.model.Storys;

import java.util.List;

/**
 * Created by luocheng on 2019/3/11.
 */
public interface StoryService {

    public void insert(Storys modal);

    List<Storys> queryData(Integer type);

    Integer getCountByType(Integer type);

    void delById(Integer id);

    void insertStoryCat(StoryCat model);

    List<StoryCat> queryCatData();
}
