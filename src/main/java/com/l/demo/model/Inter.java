package com.l.demo.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public class Inter {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String pass;

    @Getter
    @Setter
    private MultipartFile  file;



    @Override
    public String toString() {
        return "Inter{" +
                "name='" + name + '\'' +
                ", pass='" + pass + '\'' +
                ", file=" + file.getName() +
                '}';
    }
}