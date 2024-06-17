package cn.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import cn.project.mapper.uMapper;
import cn.project.model.User;

@Controller
public class ViewController {
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/{path}")
    public String path(@PathVariable String path) {
        return path;
    }
}
