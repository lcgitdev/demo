package com.l.demo.controller;

import com.l.demo.model.PhotoAlbum;
import com.l.demo.model.Photos;
import com.l.demo.service.PhotoService;
import com.l.demo.util.ContantsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by luocheng on 2019/3/1.
 */
@RestController
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @PutMapping("/photo/addPhotoAlbum")
    public String addPhotoAlbum(PhotoAlbum model,@RequestParam("realFile") MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename(); //获取文件名
            if(!fileName.endsWith("bmp") && !fileName.endsWith("png")  && !fileName.endsWith("gif")
                    && !fileName.endsWith("jpg")  && !fileName.endsWith("jpeg")){
                return "error";
            }

            //背景图片
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String date = sdf.format(new Date());
            //String road = "F:\\UcapProject\\demo\\images\\" + date;

            String road = ContantsUtil.IMAGE_SAVE_URL + date;

            String path = uploadUtils(file, road);
            if (null != path) {
                //上传成功
                model.setPath(path);
            } else {
                //上传失败
                return "error";
            }

            //添加数据
            //未删除的数据
            Integer userId = BaseController.getCurUserId();
            model.setUserId(userId);
            model.setIsDel(0);
            photoService.insertAlbum(model);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }


    @PostMapping("/photo/deByAlbumId/{id}")
    @ResponseBody
    public Object deByAlbumId(@PathVariable Integer id) {
        try {
            photoService.delAlbumById(id);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @GetMapping("/photo/getAlbumList")
    public Object getAlbumList() {
        try {
            List<PhotoAlbum> list = photoService.getAlbumList();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/photo/getPhotosByAlbumId/{id}")
    public Object getPhotosByAlbumId(@PathVariable Integer id) {
        try {
            List<Photos> list = photoService.getPhotosByAlbumId(id);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @PostMapping("/photo/upload/{id}")
    public Object upload(@PathVariable String id,@RequestPart(value = "file", required = false) MultipartFile[] file, HttpServletRequest request) {
        Map<String,String> returnMap = new HashMap<String,String>();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String date = sdf.format(new Date());
            String road = ContantsUtil.IMAGE_SAVE_URL + date;

            Thread.sleep(2000);

            if(uploadUtils(id,file, road)){
                //上传成功
                returnMap.put("state","1");
                returnMap.put("path","2222222222");
            }else{
                //上传失败
                returnMap.put("state","0");
                returnMap.put("errmsg","上传失败");
            }

            return returnMap;
        } catch (Exception e) {
            e.printStackTrace();
            returnMap.put("state","0");
            returnMap.put("errmsg","上传失败");
            return returnMap;
        }
    }


    public boolean uploadUtils(String id,MultipartFile[] uploadfile, String road) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String date = sdf.format(new Date());

        if (uploadfile != null && uploadfile.length > 0) {
            for (MultipartFile file : uploadfile) {

                //判断文件夹是否存在
                File directory = new File(road);
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                //获取文件名
                String fileName = file.getOriginalFilename(); //获取文件名
                if(!fileName.endsWith("bmp") && !fileName.endsWith("png")  && !fileName.endsWith("gif")
                        && !fileName.endsWith("jpg")  && !fileName.endsWith("jpeg")){
                    return false;
                }

                //创建文件夹
                File uploadtargetFile = new File(road, fileName);

                Integer userId = BaseController.getCurUserId();

                //保存文件
                if (saveFile(file, uploadtargetFile)) {
                    //数据存储
                    Photos photos = new Photos();
                    photos.setAlbumId(id);
                    photos.setIsDel(0);
                    photos.setName(fileName);
                    photos.setUserId(userId);
                    String path = "/image/" + date + "/" + fileName;
                    photos.setPath(path);

                  //  photos.setPath(road + "\\" + fileName);


                    photoService.addPhoto(photos);

                    return true;
                } else {
                   return false;
                }

            }

        }
        return false;
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

}
