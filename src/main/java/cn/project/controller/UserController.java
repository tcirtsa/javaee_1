package cn.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import cn.project.mapper.*;
import cn.project.model.*;

@Controller("user")
public class UserController {
    private uMapper uMapper;
    private aMapper aMapper;

    public UserController(uMapper uMapper, aMapper aMapper) {
        this.uMapper = uMapper;
        this.aMapper = aMapper;
    }

    @GetMapping("apparatus")
    public String equipment(Model model) {
        return "redirect:/apparatus";
    }
}
