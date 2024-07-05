package cn.project.jtee.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.project.jtee.model.User;
import cn.project.jtee.service.impl.LoginAndRegisterImpl;

@Controller
public class LoginController {
    private LoginAndRegisterImpl loginAndRegister;

    public LoginController(LoginAndRegisterImpl loginAndRegister) {
        this.loginAndRegister = loginAndRegister;
    }

    @ResponseBody
    @PostMapping("login_check")
    public ResponseEntity<?> login_check(@RequestBody User user) {
        return loginAndRegister.login_check(user);
    }

    @ResponseBody
    @PostMapping("reset_psd")
    public ResponseEntity<?> reset_psd(@RequestBody User user) {
        return loginAndRegister.reset_psd(user);
    }
}
