package cn.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.project.mapper.uMapper;
import cn.project.model.User;

@Controller
public class UserController {
    private uMapper uMapper;

    public UserController(uMapper uMapper) {
        this.uMapper = uMapper;
    }

    @GetMapping("user")
    public String user(@RequestParam("account") String account, Model model) {
        User user = uMapper.findByAccount(account);
        model.addAttribute("user", user);
        return "user";
    }
}
