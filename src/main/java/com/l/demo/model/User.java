package com.l.demo.model;

import lombok.Getter;
import lombok.Setter;

public class User {
    @Getter
    @Setter
    private Integer id;

    @Getter
    @Setter
    private String userName;

    @Getter
    @Setter
    private String realName;

    @Getter
    @Setter
    private String passWord;

    @Getter
    @Setter
    private String imgPath;

    @Getter
    @Setter
    private String remark;

    @Getter
    @Setter
    private Integer isDel;

    //is_curd
    @Getter
    @Setter
    private Integer isCurd;


}