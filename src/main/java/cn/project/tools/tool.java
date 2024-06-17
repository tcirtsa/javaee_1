package cn.project.tools;

import org.springframework.web.multipart.MultipartFile;

public class tool {
    public static String getFileExtension(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (fileName != null && fileName.lastIndexOf(".") != -1) {
            return fileName.substring(fileName.lastIndexOf("."));
        } else {
            return "";
        }
    }
}
