package cn.project.jtee.model;

import lombok.Data;

@Data
public class User {
    private String account;
    private String name;
    private String password;
    private String phone;
    private String address;
    private Integer authority;
    private String head;
}
