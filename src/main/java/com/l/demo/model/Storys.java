package com.l.demo.model;

import lombok.Getter;
import lombok.Setter;

public class Storys {
    @Getter
    @Setter
    private Integer id;

    @Getter
    @Setter
    private Integer type;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String content;

    @Getter
    @Setter
    private String imgPath;

    @Getter
    @Setter
    private Integer isDel;

    @Getter
    @Setter
    private Integer userId;
}