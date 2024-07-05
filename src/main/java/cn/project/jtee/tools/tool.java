package cn.project.jtee.tools;

import org.springframework.web.multipart.MultipartFile;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class tool {
    public static String getFileExtension(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (fileName != null && fileName.lastIndexOf(".") != -1) {
            return fileName.substring(fileName.lastIndexOf("."));
        } else {
            return "";
        }
    }

    public static String replacePathWithImages(String input) {
        if (input == null) {
            return null;
        }
        String regex = "src\\\\main\\\\webapp\\\\images"; // 正则表达式匹配给定的路径
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.replaceAll("images"); // 替换所有匹配的为images
    }
}
