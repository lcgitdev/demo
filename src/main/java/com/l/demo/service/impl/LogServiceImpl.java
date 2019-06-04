package com.l.demo.service.impl;

import com.l.demo.mapper.LogMapper;
import com.l.demo.model.Log;
import com.l.demo.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by luocheng on 2019/3/11.
 */
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogMapper logMapper;

  
    @Override
    public void insert(Log modal) {
        logMapper.insert(modal);
    }


}
