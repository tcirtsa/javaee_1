package cn.project.jtee.service;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import cn.project.jtee.model.User;

/**
 * @Author tcirtsa
 * @Date 2024/7/2
 * @Description
 */
public interface LoginAndRegister {
    public ResponseEntity<?> login_check(User user);

    public ResponseEntity<?> reset_psd(User user);

    public String register(MultipartFile image, String account, String password, String name, String phone,
            String address)throws IOException;
}
