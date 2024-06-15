package cn.project.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;

import cn.project.mapper.*;
import cn.project.model.*;

@RestController
public class UserController {
    private static final String UPLOAD_DIRECTORY = "D:\\BaiduNetdiskDownload\\images";

    private final uMapper uMapper;

    public UserController(uMapper uMapper) {
        this.uMapper = uMapper;
    }

    private String getFileExtension(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (fileName != null && fileName.lastIndexOf(".") != -1) {
            return fileName.substring(fileName.lastIndexOf("."));
        } else {
            return "";
        }
    }

    @PostMapping("upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file,
                                                   @RequestParam("fileName") String FileName) {
        if (!file.isEmpty()) {
            try {
                // 创建文件对象
                File dest = new File(UPLOAD_DIRECTORY + File.separator + FileName + getFileExtension(file));
                // 检查上传目录是否存在，如果不存在则创建
                dest.getParentFile().mkdirs();
                // 将上传的文件保存到指定的路径
                Files.copy(file.getInputStream(), Paths.get(dest.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
                uMapper.updateHead(FileName,UPLOAD_DIRECTORY + File.separator + FileName + getFileExtension(file));
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

    @GetMapping("get_all_accounts")
    public ResponseEntity<?> get_all_accounts() {
        List<User> users;
        try {
            users=uMapper.findAll();
            return ResponseEntity.ok(users);
        }catch (Exception e){
            return ResponseEntity.badRequest().body("获取失败");
        }
    }

    @PostMapping("update_psd")
    public ResponseEntity<?> update_psd(@RequestBody User user) {
        try {
            uMapper.updatePassword(user.getAccount(), user.getPassword());
            return ResponseEntity.ok("修改密码成功");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("修改密码失败");
        }
    }

    @PostMapping("insert_user")
    public ResponseEntity<?> insert_user(@RequestBody User user) {
        try {
            uMapper.insert_user(user);
            return ResponseEntity.ok("insert success");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("insert fail");
        }
    }

    @DeleteMapping("delete_account")
    public ResponseEntity<?> delete_account(@RequestBody User user) {
        try {
            uMapper.deleteAccount(user.getAccount());
            return ResponseEntity.ok("删除成功");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("删除失败");
        }
    }

    @PostMapping("query")
    public ResponseEntity<?> query(@RequestBody User user) {
        try {
            return ResponseEntity.ok(uMapper.findByAccount(user.getAccount()));
        }catch (Exception e){
            return ResponseEntity.badRequest().body("查询失败");
        }
    }

    @PostMapping("sort")
    public ResponseEntity<?> sort(@RequestBody User user) {
        List<User> users;
        try {
            if (Objects.equals(user.getAccount(), "account")){
                if(Objects.equals(user.getPassword(), "true")){
                    users = uMapper.sortAA();
                }else{
                    users = uMapper.sortAD();
                }
            }else if (Objects.equals(user.getAccount(), "password")){
                if (Objects.equals(user.getPassword(), "true")){
                    users = uMapper.sortPA();
                }else{
                    users = uMapper.sortPD();
                }
            }else if (Objects.equals(user.getAccount(), "name")){
                if (Objects.equals(user.getPassword(), "true")){
                    users = uMapper.sortNA();
                }else{
                    users = uMapper.sortND();
                }
            }else{
                return ResponseEntity.badRequest().body("排序失败");
            }
            return ResponseEntity.ok(users);
        }catch (Exception e){
            return ResponseEntity.badRequest().body("排序失败");
        }
    }

    @PostMapping("image")
    public ResponseEntity<Resource> handleImageUpload(@RequestBody User user) {
        try {
            Path path = Paths.get(user.getHead());
            Resource resource = new UrlResource(path.toUri());

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
