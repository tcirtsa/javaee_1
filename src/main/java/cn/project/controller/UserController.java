package cn.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.project.mapper.uMapper;
import cn.project.model.User;
import cn.project.tools.tool;

@Controller
public class UserController {
    private uMapper uMapper;

    public UserController(uMapper uMapper) {
        this.uMapper = uMapper;
    }

    @GetMapping("user")
    public String user(@RequestParam("account") String account, Model model) {
        User user = uMapper.findByAccount(account);
        String head = user.getHead();
        head = tool.replacePathWithImages(head);
        user.setHead(head);
        model.addAttribute("user", user);
        return "user";
    }
}
