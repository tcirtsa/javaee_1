package cn.project.jtee.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.project.jtee.mapper.PayMapper;
import cn.project.jtee.model.Pay;
import cn.project.jtee.service.PayServer;
import cn.project.jtee.tools.tool;
import lombok.Getter;

@Service
@PropertySource("classpath:globalApplication.properties")
public class PayServiceImpl implements PayServer {
    @Value("${UPLOAD_DIRECTORY}")
    @Getter
    public String UPLOAD_DIRECTORY;

    private PayMapper payMapper;

    public PayServiceImpl(PayMapper payMapper) {
        this.payMapper = payMapper;
    }

    @Override
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(payMapper.findAll());
    }

    @Override
    public ResponseEntity<?> findByWho(String who) {
        if (payMapper.findByWho(who) == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(payMapper.findByWho(who));
        }
    }

    @Override
    public ResponseEntity<?> updateImage(MultipartFile file, Integer id, String pay, String description,String who) {
        if (!file.isEmpty()) {
            try {
                // 创建文件对象
                File dest = new File(UPLOAD_DIRECTORY + File.separator + id + "_" + who + tool.getFileExtension(file));
                // 检查上传目录是否存在，如果不存在则创建
                dest.getParentFile().mkdirs();
                // 将上传的文件保存到指定的路径
                Files.copy(file.getInputStream(), Paths.get(dest.getAbsolutePath()),
                        StandardCopyOption.REPLACE_EXISTING);
                payMapper.updateImage(id, UPLOAD_DIRECTORY + File.separator + id + "_" + who + tool.getFileExtension(file), pay, description);
                // 返回成功消息
                return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
            } catch (IOException e) {
                // 处理文件上传失败的情况
                return new ResponseEntity<>("Error uploading file", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            // 返回文件为空的消息
            return new ResponseEntity<>("Error: No file uploaded", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> insertPay(Pay pay) {
        if (payMapper.insertPay(pay)) {
            payMapper.insertPay(pay);
            return ResponseEntity.ok("insert success");
        }else {
            return ResponseEntity.badRequest().body("insert failed");
        }
    }
    

    @Override
    public ResponseEntity<?> deleteByID(Integer integer) {
        if (payMapper.deleteByID(integer)) {
            return ResponseEntity.ok("delete success");
        }else {
            return ResponseEntity.badRequest().body("delete failed");
        }
    }
}
