package cn.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.project.mapper.uMapper;
import cn.project.model.User;

@Controller
public class LoginController {
    private uMapper uMapper;

    public LoginController(uMapper uMapper) {
        this.uMapper = uMapper;
    }

    @PostMapping("login")
    public String login(@RequestParam("account") String account, @RequestParam("password") String password,
            @RequestParam("role") String role,
            RedirectAttributes redirectAttributes, Model model) {
        User user = uMapper.findByAccount(account);
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "account does not exist。");
            return "redirect:/login";
        } else if (!user.getPassword().equals(password)) {
            redirectAttributes.addFlashAttribute("error", "Password incorrect。");
            return "redirect:/login";
        } else if ("管理员登录".equals(role)) {
            if (user.getAuthority().equals(3)) {
                redirectAttributes.addFlashAttribute("account", account);
                return "redirect:/controlall";
            } else if (user.getAuthority().equals(2)) {
                redirectAttributes.addFlashAttribute("account", account);
                return "redirect:/control";
            } else if (user.getAuthority().equals(1)) {
                redirectAttributes.addFlashAttribute("account", account);
                return "redirect:/control";
            } else if (user.getAuthority().equals(0)) {
                redirectAttributes.addFlashAttribute("error", "Permission denied。");
                redirectAttributes.addFlashAttribute("account", account);
                return "redirect:/user";
            } else {
                redirectAttributes.addFlashAttribute("error", "Permission denied。");
                return "redirect:/login";
            }
        } else {
            redirectAttributes.addFlashAttribute("account", account);
            return "redirect:/user";
        }
    }
}
