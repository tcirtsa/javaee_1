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
import cn.project.tools.*;

@RestController
public class TestController {
    private static final String UPLOAD_DIRECTORY = "src\\main\\webapp\\images";

    private final uMapper uMapper;
    private final aMapper aMapper;

    public TestController(uMapper uMapper, aMapper aMapper) {
        this.uMapper = uMapper;
        this.aMapper = aMapper;
    }

    @PostMapping("upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file,
            @RequestParam("fileName") String FileName) {
        if (!file.isEmpty()) {
            try {
                // 创建文件对象
                File dest = new File(UPLOAD_DIRECTORY + File.separator + FileName + tool.getFileExtension(file));
                // 检查上传目录是否存在，如果不存在则创建
                dest.getParentFile().mkdirs();
                // 将上传的文件保存到指定的路径
                Files.copy(file.getInputStream(), Paths.get(dest.getAbsolutePath()),
                        StandardCopyOption.REPLACE_EXISTING);
                uMapper.updateHead(FileName,
                        UPLOAD_DIRECTORY + File.separator + FileName + tool.getFileExtension(file));
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
            users = uMapper.findAll();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("获取失败");
        }
    }

    @GetMapping("get_users")
    public ResponseEntity<?> get_users() {
        List<User> users;
        try {
            users = uMapper.findByAuthority();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("获取失败");
        }
    }

    @PostMapping("update_psd")
    public ResponseEntity<?> update_psd(@RequestBody User user) {
        try {
            uMapper.updatePassword(user.getAccount(), user.getPassword());
            return ResponseEntity.ok("修改密码成功");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("修改密码失败");
        }
    }

    @PostMapping("update_authority")
    public ResponseEntity<?> update_authority(@RequestBody User user) {
        try {
            if (user.getAuthority().equals(0) || user.getAuthority().equals(1) || user.getAuthority().equals(2)) {
                uMapper.updateAuthority(user.getAccount(), user.getAuthority());
                return ResponseEntity.ok("修改权限成功");
            } else {
                return ResponseEntity.badRequest().body("修改权限失败");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("修改权限失败");
        }
    }

    @PostMapping("insert_user")
    public ResponseEntity<?> insert_user(@RequestParam("image") MultipartFile image,
            @RequestParam("account") String account, @RequestParam("name") String name,
            @RequestParam("password") String password, @RequestParam("phone") String phone,
            @RequestParam("address") String address, @RequestParam("authority") Integer authority) {
        try {
            User user = new User();
            user.setAccount(account);
            user.setName(name);
            user.setPassword(password);
            user.setPhone(phone);
            user.setAddress(address);
            if (authority == 0) {
                authority = 0;
            } else if (authority == 1) {
                authority = 1;
            } else {
                authority = 0;
            }
            user.setAuthority(authority);
            user.setHead(UPLOAD_DIRECTORY + File.separator + account + tool.getFileExtension(image));
            File dest = new File(UPLOAD_DIRECTORY + File.separator + account + tool.getFileExtension(image));
            // 检查上传目录是否存在，如果不存在则创建
            dest.getParentFile().mkdirs();
            // 将上传的文件保存到指定的路径
            Files.copy(image.getInputStream(), Paths.get(dest.getAbsolutePath()),
                    StandardCopyOption.REPLACE_EXISTING);
            uMapper.insert_user(user);
            return ResponseEntity.ok("insert success");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("insert fail");
        }
    }

    @DeleteMapping("delete_account")
    public ResponseEntity<?> delete_account(@RequestBody User user) {
        try {
            uMapper.deleteAccount(user.getAccount());
            return ResponseEntity.ok("删除成功");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("删除失败");
        }
    }

    @PostMapping("query")
    public ResponseEntity<?> query(@RequestBody User user) {
        try {
            return ResponseEntity.ok(uMapper.findByAccount(user.getAccount()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("查询失败");
        }
    }

    @PostMapping("query_users")
    public ResponseEntity<?> query_users(@RequestBody User user) {
        try {
            return ResponseEntity.ok(uMapper.findByAuthorityAndAccount(user.getAccount()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("查询失败");
        }
    }

    @PostMapping("sort")
    public ResponseEntity<?> sort(@RequestBody User user) {
        List<User> users;
        try {
            if (Objects.equals(user.getAccount(), "account")) {
                if (Objects.equals(user.getPassword(), "true")) {
                    users = uMapper.sortAA();
                } else {
                    users = uMapper.sortAD();
                }
            } else if (Objects.equals(user.getAccount(), "password")) {
                if (Objects.equals(user.getPassword(), "true")) {
                    users = uMapper.sortPA();
                } else {
                    users = uMapper.sortPD();
                }
            } else if (Objects.equals(user.getAccount(), "name")) {
                if (Objects.equals(user.getPassword(), "true")) {
                    users = uMapper.sortNA();
                } else {
                    users = uMapper.sortND();
                }
            } else {
                return ResponseEntity.badRequest().body("排序失败");
            }
            return ResponseEntity.ok(users);
        } catch (Exception e) {
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

    @PostMapping("sort_users")
    public ResponseEntity<?> sort_users(@RequestBody User user) {
        List<User> users;
        try {
            if (Objects.equals(user.getAccount(), "account")) {
                if (Objects.equals(user.getPassword(), "true")) {
                    users = uMapper.sortUAA();
                } else {
                    users = uMapper.sortUAD();
                }
            } else if (Objects.equals(user.getAccount(), "password")) {
                if (Objects.equals(user.getPassword(), "true")) {
                    users = uMapper.sortUPA();
                } else {
                    users = uMapper.sortUPD();
                }
            } else if (Objects.equals(user.getAccount(), "name")) {
                if (Objects.equals(user.getPassword(), "true")) {
                    users = uMapper.sortUNA();
                } else {
                    users = uMapper.sortUND();
                }
            } else {
                return ResponseEntity.badRequest().body("排序失败");
            }
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("排序失败");
        }
    }

    @GetMapping("get_all_apparatus")
    public ResponseEntity<?> get_all_apparatus() {
        try {
            List<Apparatus> apparatus = aMapper.findAll();
            return ResponseEntity.ok(apparatus);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("获取失败");
        }
    }

    @PostMapping("query_apparatus")
    public ResponseEntity<?> query_apparatus(@RequestBody Apparatus apparatus) {
        try {
            return ResponseEntity.ok(aMapper.findByID(apparatus.getId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("查询失败");
        }
    }

    @PostMapping("image_apparatus")
    public ResponseEntity<Resource> image_apparatus(@RequestBody Apparatus apparatus) {
        try {
            Path path = Paths.get(apparatus.getImage());
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

    @PostMapping("rent_apparatus")
    public ResponseEntity<?> rent_apparatus(@RequestBody Apparatus apparatus) {
        try {
            Apparatus a = aMapper.findByID(apparatus.getId());
            a.setWho(apparatus.getWho());
            a.setStatus(1);
            aMapper.lendByID(a);
            return ResponseEntity.ok("租用成功");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("租用失败");
        }
    }

    @PostMapping("return_apparatus")
    public ResponseEntity<?> return_apparatus(@RequestBody Apparatus apparatus) {
        try {
            Apparatus a = aMapper.findByID(apparatus.getId());
            if (a.getWho().equals(apparatus.getWho())) {
                a.setWho("");
                a.setStatus(0);
                aMapper.returnByID(a);
                return ResponseEntity.ok("归还成功");
            } else {
                return ResponseEntity.ok().body("归还失败");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("归还失败");
        }
    }

    @PostMapping("repair_apparatus")
    public ResponseEntity<?> repair_apparatus(@RequestBody Apparatus apparatus) {
        try {
            Apparatus a = aMapper.findByID(apparatus.getId());
            a.setStatus(0);
            aMapper.returnByID(a);
            return ResponseEntity.ok("repair success");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("repair failed");
        }
    }

    @PostMapping("ToRepair_apparatus")
    public ResponseEntity<?> ToRepair_apparatus(@RequestBody Apparatus apparatus) {
        try {
            Apparatus a = aMapper.findByID(apparatus.getId());
            a.setStatus(2);
            a.setWho(apparatus.getWho());
            aMapper.lendByID(a);
            return ResponseEntity.ok("维修成功");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("维修失败");
        }
    }

    @PostMapping("delete_apparatus")
    public ResponseEntity<?> delete_apparatus(@RequestBody Apparatus apparatus) {
        try {
            aMapper.deleteByID(apparatus.getId());
            return ResponseEntity.ok("删除成功");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("删除失败");
        }
    }

    @PostMapping("upload_apparatus_image")
    public ResponseEntity<String> upload_apparatus_image(@RequestParam("file") MultipartFile file,
            @RequestParam("fileName") String FileName) {
        if (!file.isEmpty()) {
            try {
                // 创建文件对象
                File dest = new File(UPLOAD_DIRECTORY + File.separator + FileName + tool.getFileExtension(file));
                // 检查上传目录是否存在，如果不存在则创建
                dest.getParentFile().mkdirs();
                // 将上传的文件保存到指定的路径
                Files.copy(file.getInputStream(), Paths.get(dest.getAbsolutePath()),
                        StandardCopyOption.REPLACE_EXISTING);
                aMapper.updateImage(FileName,
                        UPLOAD_DIRECTORY + File.separator + FileName + tool.getFileExtension(file));
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

    @PostMapping("insert_apparatus")
    public ResponseEntity<?> insert_apparatus(@RequestBody Apparatus apparatus) {
        try {
            aMapper.addApparatus(apparatus);
            return ResponseEntity.ok("添加成功");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("添加失败");
        }
    }
}
