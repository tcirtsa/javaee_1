package cn.project.jtee.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.project.jtee.mapper.UserMapper;
import cn.project.jtee.model.User;
import cn.project.jtee.service.LoginAndRegister;
import cn.project.jtee.tools.tool;

@Service
@PropertySource("classpath:globalApplication.properties")
public class LoginAndRegisterImpl implements LoginAndRegister {
    private static final String UPLOAD_DIRECTORY = "src\\main\\webapp\\images";
    private UserMapper uMapper;

    public LoginAndRegisterImpl(UserMapper uMapper) {
        this.uMapper = uMapper;
    }

    @Override
    public ResponseEntity<?> login_check(User user) {
        User user1 = uMapper.findByAccount(user.getAccount());
        if (user1 == null) {
            return ResponseEntity.badRequest().body("account not found");
        }
        if (user1.getPassword().equals(user.getPassword())) {
            return ResponseEntity.ok(user1.getAuthority());
        }
        return ResponseEntity.badRequest().body("password error");
    }

    @Override
    public ResponseEntity<?> reset_psd(User user) {
        User user1 = uMapper.findByAccount(user.getAccount());
        if (user1 == null) {
            return ResponseEntity.badRequest().body("account not found");
        } else if (!user1.getPassword().equals(user.getPassword())) {
            return ResponseEntity.badRequest().body("password error");
        }
        uMapper.updatePassword(user1.getAccount(), user.getAddress());
        return ResponseEntity.ok("update success");
    }

    @Override
    public String register(MultipartFile image, String account, String password, String name, String phone,
            String address) throws IOException {
        User user = uMapper.findByAccount(account);
        if (user != null) {
            return "redirect:/register";
        }
        User newUser = new User();
        newUser.setAccount(account);
        newUser.setPassword(password);
        newUser.setName(name);
        newUser.setPhone(phone);
        newUser.setAddress(address);
        newUser.setAuthority(0);
        newUser.setHead(UPLOAD_DIRECTORY + File.separator + account + tool.getFileExtension(image));
        File dest = new File(UPLOAD_DIRECTORY + File.separator + account + tool.getFileExtension(image));
        // 检查上传目录是否存在，如果不存在则创建
        dest.getParentFile().mkdirs();
        // 将上传的文件保存到指定的路径
        Files.copy(image.getInputStream(), Paths.get(dest.getAbsolutePath()),
                StandardCopyOption.REPLACE_EXISTING);
        uMapper.insert_user(newUser);
        return "redirect:/login";
    }
}
