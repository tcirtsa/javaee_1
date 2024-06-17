package cn.project.controller;

import org.springframework.stereotype.Controller;
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
            RedirectAttributes redirectAttributes) {
        User user = uMapper.findByAccount(account);
        if (user == null) {
            redirectAttributes.addFlashAttribute("error", "account does not exist。");
            return "redirect:/login";
        } else if (!user.getPassword().equals(password)) {
            redirectAttributes.addFlashAttribute("error", "Password incorrect。");
            return "redirect:/login";
        } else {
            return "redirect:/user?account=" + account;
        }
    }
}
