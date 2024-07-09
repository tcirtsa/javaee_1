package cn.project.jtee.service;


import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import cn.project.jtee.model.Pay;

public interface PayServer {
    ResponseEntity<?> findAll();

    ResponseEntity<?> findByWho(String who);

    ResponseEntity<?> updateImage(MultipartFile file, Integer id, String pay, String description,String who);

    ResponseEntity<?> insertPay(Pay pay);

    ResponseEntity<?> deleteByID(Integer id);
}
