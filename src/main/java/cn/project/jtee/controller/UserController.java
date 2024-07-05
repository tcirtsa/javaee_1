package cn.project.jtee.controller;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.project.jtee.model.User;
import cn.project.jtee.selfEnum.SelectLevel;
import cn.project.jtee.service.impl.FileServerImpl;
import cn.project.jtee.service.impl.UserMapperServiceImpl;
import cn.project.jtee.tools.tool;

@RestController
public class UserController {
    private FileServerImpl fileServer;
    private UserMapperServiceImpl userServer;

    public UserController(FileServerImpl fileServer, UserMapperServiceImpl userServer) {
        this.fileServer = fileServer;
        this.userServer = userServer;
    }

    @PostMapping("upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file,
            @RequestParam("fileName") String FileName) {
        return fileServer.uploadFile(file, FileName);
    }

    @GetMapping("get_all_accounts")
    public ResponseEntity<?> get_all_accounts() {
        List<User> allAccounts = userServer.getAllAccountsByLevel(SelectLevel.All);
        return allAccounts != null ? ResponseEntity.ok(allAccounts) : ResponseEntity.badRequest().body("获取失败");
    }

    @GetMapping("get_users")
    public ResponseEntity<?> get_users() {
        List<User> accounts = userServer.getAllAccountsByLevel(SelectLevel.MaintenanceLevel);
        return accounts == null ? ResponseEntity.badRequest().body("获取失败") : ResponseEntity.ok(accounts);
    }

    @PostMapping("update_psd")
    public ResponseEntity<?> update_psd(@RequestBody User user) {
        return userServer.updateUser(user, "password");
    }

    @PostMapping("update_authority")
    public ResponseEntity<?> update_authority(@RequestBody User user) {
        if (user.getAuthority().equals(0) || user.getAuthority().equals(1) || user.getAuthority().equals(2)) {
            return userServer.updateUser(user, "authority");
        } else {
            return ResponseEntity.badRequest().body("修改权限失败");
        }
    }

    @PostMapping("insert_user")
    public ResponseEntity<?> insert_user(@RequestParam("image") MultipartFile image,
            @RequestParam("account") String account, @RequestParam("name") String name,
            @RequestParam("password") String password, @RequestParam("phone") String phone,
            @RequestParam("address") String address, @RequestParam("authority") Integer authority) {
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
        user.setHead(fileServer.getUPLOAD_DIRECTORY() + File.separator + account + tool.getFileExtension(image));
        fileServer.uploadFile(image, account);
        return userServer.insertUser(user);
    }

    @DeleteMapping("delete_account")
    public ResponseEntity<?> delete_account(@RequestBody User user) {
        return userServer.deleteAccount(user);
    }

    @PostMapping("query")
    public ResponseEntity<?> query(@RequestBody User user) {
        return userServer.queryUser(user, "user");
    }

    @PostMapping("query_users")
    public ResponseEntity<?> query_users(@RequestBody User user) {
        return userServer.queryUser(user, "authority");
    }

    @PostMapping("sort")
    public ResponseEntity<?> sort(@RequestBody User user) {
        return userServer.sort(user);
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
        return userServer.sort_users(user);
    }
}
