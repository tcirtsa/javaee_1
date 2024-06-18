package cn.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cn.project.mapper.uMapper;
import cn.project.model.User;
import cn.project.tools.tool;

@Controller
public class ViewController {

    private uMapper uMapper;

    public ViewController(uMapper uMapper) {
        this.uMapper = uMapper;
    }

    @GetMapping("/")
    public String index() {
        return "login";
    }

    @GetMapping("/{path}")
    public String path(@PathVariable String path) {
        return path;
    }

    @GetMapping("control")
    public String control(Model model) {
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
        return "control";
    }

    @GetMapping("user")
    public String user(Model model) {
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
        return "user";
    }
}
