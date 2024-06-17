package cn.project.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import cn.project.mapper.uMapper;
import cn.project.model.User;
import cn.project.tools.tool;

@Controller
public class RegisterController {
    private static final String UPLOAD_DIRECTORY = "src\\main\\webapp\\images";
    private uMapper uMapper;

    public RegisterController(uMapper uMapper) {
        this.uMapper = uMapper;
    }

    @PostMapping("register")
    public String register(@RequestParam("image") MultipartFile image, @RequestParam("account") String account,
            @RequestParam("password") String password,
            @RequestParam("name") String name, @RequestParam("phone") String phone,
            @RequestParam("address") String address) throws IOException {
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
