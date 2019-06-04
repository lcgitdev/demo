package com.l.demo.controller;

import com.l.demo.model.Bbs;
import com.l.demo.model.PhotoAlbum;
import com.l.demo.service.BbsService;
import com.l.demo.service.PhotoService;
import com.l.demo.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by luocheng on 2019/3/1.
 */
@RestController
public class BbsController {

    @Autowired
    private BbsService bbsService;

    @Autowired
    private HttpServletRequest request;


    @PostMapping("/bbs/add")
    public String add(Bbs model) {
        try {
            //添加数据
         //   Bbs model = new Bbs();
         //   model.setBbsName(bbsName);
         //   model.setBbsCont(bbsCont);
            Integer userId = BaseController.getCurUserId();
            model.setUserId(userId);

            model.setCreateTime(DateUtils.getNowDate());
            bbsService.inserBbs(model);
            return "添加成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "添加失败，发送异常";
        }
    }

    @GetMapping("/bbs/list/{nowDate}")
    public List<Bbs> list(@PathVariable String nowDate) {
        try {
            List<Bbs> list = bbsService.queryBbsData(nowDate);

            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
