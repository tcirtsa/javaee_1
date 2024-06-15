package cn.project.model;

import lombok.Data;

import java.util.Date;

@Data
public class Apparatus {
    private String id;
    private String name;
    private String type;
    private Integer status=0;
    private String who;
    private String address;
    private String description;
    private Date time;
    private String image="D:\\BaiduNetdiskDownload\\images\\default.jpg";
}
