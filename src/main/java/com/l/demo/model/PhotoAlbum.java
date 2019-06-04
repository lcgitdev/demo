package com.l.demo.model;

import lombok.Getter;
import lombok.Setter;

public class PhotoAlbum {

    @Getter @Setter private Integer id;

    @Getter @Setter private String name;

    @Getter @Setter private String remark;

    @Getter @Setter private Integer isDel;

    @Getter @Setter private  String path;


    @Getter
    @Setter
    private Integer pCount;

    @Getter
    @Setter
    private Integer userId;
}