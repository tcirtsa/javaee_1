package cn.project.jtee.service.impl;

import cn.project.jtee.model.Apparatus;
import cn.project.jtee.service.ApparatusMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.project.jtee.mapper.ApparatusMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

import cn.project.jtee.tools.tool;

@Service
public class ApparatusMapperServiceImpl implements ApparatusMapperService {
    private ApparatusMapper apparatusMapperMapper;
    private FileServerImpl fileServer;

    @Autowired
    public ApparatusMapperServiceImpl(ApparatusMapper apparatusMapperMapper, FileServerImpl fileServer) {
        this.apparatusMapperMapper = apparatusMapperMapper;
        this.fileServer = fileServer;
    }

    @Override
    public ResponseEntity<?> getAllApparatus() {
        try {
            List<Apparatus> apparatus = apparatusMapperMapper.findAll();
            return ResponseEntity.ok(apparatus);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("fail to get");
        }
    }

    public ResponseEntity<?> rent_apparatus(Apparatus apparatus) {
        try {
            Apparatus a = apparatusMapperMapper.findByID(apparatus.getId());
            a.setWho(apparatus.getWho());
            a.setStatus(1);
            a.setTime(new Date());
            apparatusMapperMapper.lendByID(a);
            return ResponseEntity.ok("rent successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("fail to rent");
        }
    }

    public ResponseEntity<?> return_apparatus(Apparatus apparatus) {
        try {
            Apparatus a = apparatusMapperMapper.findByID(apparatus.getId());
            if (a.getWho().equals(apparatus.getWho())) {
                a.setWho("");
                a.setStatus(0);
                a.setTime(new Date());
                apparatusMapperMapper.returnByID(a);
                return ResponseEntity.ok("return success");
            } else {
                return ResponseEntity.ok().body("return fail");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("return fail");
        }
    }

    public ResponseEntity<?> repair_apparatus(Apparatus apparatus) {
        try {
            Apparatus a = apparatusMapperMapper.findByID(apparatus.getId());
            a.setStatus(0);
            a.setTime(new Date());
            apparatusMapperMapper.returnByID(a);
            return ResponseEntity.ok("repair success");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("repair failed");
        }
    }

    public ResponseEntity<?> ToRepair_apparatus(Apparatus apparatus) {
        try {
            Apparatus a = apparatusMapperMapper.findByID(apparatus.getId());
            a.setStatus(2);
            a.setWho(apparatus.getWho());
            a.setTime(new Date());
            apparatusMapperMapper.lendByID(a);
            return ResponseEntity.ok("repair successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("fail to repair");
        }
    }

    public ResponseEntity<?> delete_apparatus(Apparatus apparatus) {
        try {
            apparatusMapperMapper.deleteByID(apparatus.getId());
            return ResponseEntity.ok("delete success");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("fail to delete");
        }
    }

    public ResponseEntity<?> upload_apparatus_image(MultipartFile file, String FileName) {
        if (!file.isEmpty()) {
            try {
                // 创建文件对象
                File dest = new File(
                        fileServer.getUPLOAD_DIRECTORY() + File.separator + FileName + tool.getFileExtension(file));
                // 检查上传目录是否存在，如果不存在则创建
                dest.getParentFile().mkdirs();
                // 将上传的文件保存到指定的路径
                Files.copy(file.getInputStream(), Paths.get(dest.getAbsolutePath()),
                        StandardCopyOption.REPLACE_EXISTING);
                apparatusMapperMapper.updateImage(FileName,
                        fileServer.getUPLOAD_DIRECTORY() + File.separator + FileName + tool.getFileExtension(file));
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

    public ResponseEntity<?> insert_apparatus(Apparatus apparatus) {
        try {
            apparatus.setStatus(0);
            apparatus.setTime(new Date());
            apparatusMapperMapper.addApparatus(apparatus);
            return ResponseEntity.ok("add successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("fail to add");
        }
    }

    public ResponseEntity<?> query_apparatus(Apparatus apparatus) {
        try {
            return ResponseEntity.ok(apparatusMapperMapper.findByID(apparatus.getId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("fail to query");
        }
    }
}
