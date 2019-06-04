package com.l.demo.model;

import lombok.Getter;
import lombok.Setter;

import java.beans.Transient;
import java.sql.Timestamp;
import java.util.Date;

public class Bbs {
    @Getter
    @Setter
    private Integer id;

    @Getter
    @Setter
    private String bbsName;

    @Getter
    @Setter
    private String bbsCont;

    @Getter
    @Setter
    private String createTime;

    @Getter
    @Setter
    private Integer userId;

    //虚拟字段
    private String imgPath;
    private String realName;

    public Bbs() {
    }

    public Bbs(String bbsName, String bbsCont, String createTime) {
        this.bbsName = bbsName;
        this.bbsCont = bbsCont;
        this.createTime = createTime;
    }


    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}