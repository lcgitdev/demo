package com.l.demo.controller;

import com.l.demo.model.Video;
import com.l.demo.model.Storys;
import com.l.demo.service.VideoService;
import com.l.demo.util.ContantsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by luocheng on 2019/3/1.
 */
@RestController
public class VideoController {

    @Autowired
    private VideoService videoService;


    //addVideo
    @PutMapping("/video/add")
    public Object add(Video model, @RequestParam("realFile") MultipartFile file) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            String fileName = file.getOriginalFilename(); //获取文件名
            if(!fileName.endsWith("mp4") && !fileName.endsWith("3gp")  && !fileName.endsWith("rmvb")
                    && !fileName.endsWith("wmv")  && !fileName.endsWith("ram")){
                resultMap.put("status", false);
                resultMap.put("msg", "只能选择视频文件哦");
                return resultMap;

            }
            Integer userId = BaseController.getCurUserId();
            model.setUserId(userId);
            model.setIsDel(0);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String date = sdf.format(new Date());
            String road = ContantsUtil.VIDEO_SAVE_URL + date;

            String path = uploadUtils(file, road);
            if (null != path) {
                //上传成功
                model.setVideoPath(path);
            } else {
                //上传失败
                resultMap.put("status", false);
                resultMap.put("msg", "视频添加失败");
                return resultMap;
            }

            videoService.insert(model);
            resultMap.put("status", true);
            resultMap.put("msg", "添加成功");
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("status", false);
            resultMap.put("msg", "添加失败，发生异常");
            return resultMap;
        }
    }


    @GetMapping("/video/list")
    public List<Video> listVideo() {
        try {
            List<Video> list = videoService.queryData();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @PostMapping("/video/delById/{id}")
    @ResponseBody
    public Object delById(@PathVariable Integer id) {
        try {
            videoService.delById(id);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }


    public String uploadUtils(MultipartFile file, String road) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String date = sdf.format(new Date());
        //判断文件夹是否存在
        File directory = new File(road);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        //获取文件名
        String fileName = file.getOriginalFilename(); //获取文件名
        //创建文件夹
        File uploadtargetFile = new File(road, fileName);
        //保存文件
        if (saveFile(file, uploadtargetFile)) {
            String path = "/video/" + date + "/" + fileName;
            return path;
        } else {
            return null;
        }
    }

    //判断该路径下文件是否存在
    private boolean isExists(File uploadtargetFile) {
        if (!uploadtargetFile.exists()) {
            uploadtargetFile.mkdirs();
            return true;
        } else {
            System.out.println("文件已存在");
            return false;
        }
    }

    //保存文件
    private boolean saveFile(MultipartFile file, File uploadtargetFile) {
        // 判断文件是否为空  
        if (!file.isEmpty()) {
            try {
                file.transferTo(uploadtargetFile); //写入文件
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }


}
