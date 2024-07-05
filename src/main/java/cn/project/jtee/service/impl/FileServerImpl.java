package cn.project.jtee.service.impl;

import cn.project.jtee.mapper.UserMapper;
import cn.project.jtee.service.FileServer;
import cn.project.jtee.tools.tool;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @Author yiren
 * @Date 2024/7/2
 * @Description
 */
@Service
@PropertySource("classpath:globalApplication.properties")
public class FileServerImpl implements FileServer {

    @Value("${UPLOAD_DIRECTORY}")
    @Getter
    public String UPLOAD_DIRECTORY;

    private UserMapper uMapper;

    @Autowired
    public FileServerImpl(UserMapper uMapper) {
        this.uMapper = uMapper;
    }

    @Override
    public ResponseEntity<String> uploadFile(MultipartFile file, String FileName) {
        if (!file.isEmpty()) {
            try {
                // 创建文件对象
                File dest = new File(UPLOAD_DIRECTORY + File.separator + FileName + tool.getFileExtension(file));
                // 检查上传目录是否存在，如果不存在则创建
                dest.getParentFile().mkdirs();
                // 将上传的文件保存到指定的路径
                Files.copy(file.getInputStream(), Paths.get(dest.getAbsolutePath()),
                        StandardCopyOption.REPLACE_EXISTING);
                uMapper.updateHead(FileName,
                        UPLOAD_DIRECTORY + File.separator + FileName + tool.getFileExtension(file));
                // 返回成功消息
                return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
            } catch (IOException e) {
                // 处理文件上传失败的情况
                return new ResponseEntity<>("Error uploading file", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            // 返回文件为空的消息
            return new ResponseEntity<>("Error: No file uploaded", HttpStatus.BAD_REQUEST);
        }
    }
}
