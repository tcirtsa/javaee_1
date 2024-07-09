package cn.project.jtee.model;

import java.util.Date;

import lombok.Data;

@Data
public class Pay {
    private Integer id;
    private Integer pay;
    private String who;
    private Date time;
    private String image;
    private String description;
}
