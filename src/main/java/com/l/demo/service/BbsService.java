package com.l.demo.service;

import com.l.demo.model.Bbs;
import com.l.demo.model.PhotoAlbum;
import com.l.demo.model.Photos;

import java.util.List;

/**
 * Created by luocheng on 2019/3/11.
 */
public interface BbsService {

    public int inserBbs(Bbs modal);

    List<Bbs> queryBbsData(String nowDate);
}
