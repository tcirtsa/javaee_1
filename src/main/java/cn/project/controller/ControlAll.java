package cn.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import cn.project.mapper.uMapper;
import cn.project.model.User;
import cn.project.tools.tool;

@Controller
public class ControlAll {
    private uMapper uMapper;

    public ControlAll(uMapper uMapper) {
        this.uMapper = uMapper;
    }

    @GetMapping("controlall")
    public String controlall(Model model) {
        try {
            String account = (String) model.asMap().get("account");
            User user = uMapper.findByAccount(account);
            String head = user.getHead();
            head = tool.replacePathWithImages(head);
            user.setHead(head);
            model.addAttribute("user", user);
        } catch (Exception e) {
            return "redirect:/login";
        }
        return "controlall";
    }
}
