package cn.project.jtee.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author yiren
 * @Date 2024/7/2
 * @Description
 */
public interface FileServer {
    /**
     * create by: yiren
     * description: 上传文件，如果文件路径不存在，创作新文件路径
     * create time: 2024/7/2 上午11:41
     * 
     * @params [MultipartFile file, String FileName]
     * @return ResponseEntity
     */
    ResponseEntity<String> uploadFile(MultipartFile file,String FileName);
}
