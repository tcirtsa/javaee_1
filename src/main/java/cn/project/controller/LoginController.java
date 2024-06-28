package cn.project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.project.mapper.uMapper;
import cn.project.model.User;

@Controller
public class LoginController {
    private uMapper uMapper;

    public LoginController(uMapper uMapper) {
        this.uMapper = uMapper;
    }

    @ResponseBody
    @PostMapping("login_check")
    public ResponseEntity<?> login_check(@RequestBody User user) {
        User user1 = uMapper.findByAccount(user.getAccount());
        if (user1 == null) {
            return ResponseEntity.badRequest().body("账号不存在");
        }
        if (user1.getPassword().equals(user.getPassword())) {
            return ResponseEntity.ok(user1.getAuthority());
        }
        return ResponseEntity.badRequest().body("密码错误");
    }

    @ResponseBody
    @PostMapping("reset_psd")
    public ResponseEntity<?> reset_psd(@RequestBody User user) {
        User user1 = uMapper.findByAccount(user.getAccount());
        if (user1 == null) {
            return ResponseEntity.badRequest().body("账号不存在");
        } else if (!user1.getPassword().equals(user.getPassword())) {
            return ResponseEntity.badRequest().body("原密码错误");
        }
        uMapper.updatePassword(user1.getAccount(), user.getAddress());
        return ResponseEntity.ok("修改成功");
    }
}
