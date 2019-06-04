package com.l.demo.controller;

import com.l.demo.model.User;
import com.l.demo.service.UserService;
import com.l.demo.util.ContantsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by luocheng on 2019/3/1.
 */
@RestController
public class UserController {

    @Autowired
    private UserService UserService;


    @PutMapping("/user/upd")
    public Object upd(User model, @RequestParam(value = "realFile",required = false) MultipartFile file) {
        Map<String, Object> resultMap = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String date = sdf.format(new Date());
        try {
            if(null != file){
                String fileName = file.getOriginalFilename(); //获取文件名
                if(!fileName.endsWith("bmp") && !fileName.endsWith("png")  && !fileName.endsWith("gif")
                        && !fileName.endsWith("jpg")  && !fileName.endsWith("jpeg")){
                    resultMap.put("status", false);
                    resultMap.put("msg", "只能选择图片文件哦");
                    return resultMap;
                }

                String road = ContantsUtil.IMAGE_SAVE_URL + date;

                String path = uploadUtils(file, road);
                if (null != path) {
                    //上传成功
                    model.setImgPath(path);
                } else {
                    //上传失败
                    resultMap.put("status", false);
                    resultMap.put("msg", "头像添加失败");
                    return resultMap;
                }
            }

            model.setIsDel(0);
            UserService.update(model);
            resultMap.put("status", true);
            resultMap.put("msg", "保存成功");
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("status", false);
            resultMap.put("msg", "保存失败，发生异常");
            return resultMap;
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
            String path = "/image/" + date + "/" + fileName;
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
