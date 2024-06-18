package cn.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import cn.project.mapper.uMapper;
import cn.project.model.User;
import cn.project.tools.tool;

@Controller("control")
public class Control {
    private uMapper uMapper;

    public Control(uMapper uMapper) {
        this.uMapper = uMapper;
    }
}
