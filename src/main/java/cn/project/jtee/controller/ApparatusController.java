package cn.project.jtee.controller;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.project.jtee.model.Apparatus;
import cn.project.jtee.service.impl.ApparatusMapperServiceImpl;

@RestController
public class ApparatusController {
    private ApparatusMapperServiceImpl apparatusMapperService;

    public ApparatusController(ApparatusMapperServiceImpl apparatusMapperService) {
        this.apparatusMapperService = apparatusMapperService;
    }

    @GetMapping("get_all_apparatus")
    public ResponseEntity<?> get_all_apparatus() {
        return apparatusMapperService.getAllApparatus();
    }

    @PostMapping("query_apparatus")
    public ResponseEntity<?> query_apparatus(@RequestBody Apparatus apparatus) {
        return apparatusMapperService.query_apparatus(apparatus);
    }

    @PostMapping("image_apparatus")
    public ResponseEntity<Resource> image_apparatus(@RequestBody Apparatus apparatus) {
        try {
            Path path = Paths.get(apparatus.getImage());
            Resource resource = new UrlResource(path.toUri());

            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("rent_apparatus")
    public ResponseEntity<?> rent_apparatus(@RequestBody Apparatus apparatus) {
        return apparatusMapperService.rent_apparatus(apparatus);
    }

    @PostMapping("return_apparatus")
    public ResponseEntity<?> return_apparatus(@RequestBody Apparatus apparatus) {
        return apparatusMapperService.return_apparatus(apparatus);
    }

    @PostMapping("repair_apparatus")
    public ResponseEntity<?> repair_apparatus(@RequestBody Apparatus apparatus) {
        return apparatusMapperService.repair_apparatus(apparatus);
    }

    @PostMapping("ToRepair_apparatus")
    public ResponseEntity<?> ToRepair_apparatus(@RequestBody Apparatus apparatus) {
        return apparatusMapperService.ToRepair_apparatus(apparatus);
    }

    @PostMapping("delete_apparatus")
    public ResponseEntity<?> delete_apparatus(@RequestBody Apparatus apparatus) {
        return apparatusMapperService.delete_apparatus(apparatus);
    }

    @PostMapping("upload_apparatus_image")
    public ResponseEntity<?> upload_apparatus_image(@RequestParam("file") MultipartFile file,
            @RequestParam("fileName") String FileName) {
        return apparatusMapperService.upload_apparatus_image(file, FileName);
    }

    @PostMapping("insert_apparatus")
    public ResponseEntity<?> insert_apparatus(@RequestBody Apparatus apparatus) {
        return apparatusMapperService.insert_apparatus(apparatus);
    }
}
