package cn.project.jtee.controller;

import java.io.IOException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import cn.project.jtee.service.impl.LoginAndRegisterImpl;

@Controller
public class RegisterController {
    private LoginAndRegisterImpl loginAndRegister;

    public RegisterController(LoginAndRegisterImpl loginAndRegister) {
        this.loginAndRegister = loginAndRegister;
    }

    @PostMapping("register")
    public String register(@RequestParam("image") MultipartFile image, @RequestParam("account") String account,
            @RequestParam("password") String password,
            @RequestParam("name") String name, @RequestParam("phone") String phone,
            @RequestParam("address") String address) throws IOException {
        return loginAndRegister.register(image, account, password, name, phone, address);
    }
}
