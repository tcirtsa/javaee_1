package cn.project.jtee.controller;

import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.project.jtee.model.Pay;
import cn.project.jtee.service.impl.PayServiceImpl;

@RestController
public class PayController {
    private PayServiceImpl payServer;

    public PayController(PayServiceImpl payServer) {
        this.payServer = payServer;
    }

    @GetMapping("get_all_pay")
    public ResponseEntity<?> get_all_pays() {
        return payServer.findAll();
    }

    @PostMapping("find_by_who")
    public ResponseEntity<?> find_by_who(@RequestBody Pay pay) {
        return payServer.findByWho(pay.getWho());
    }

    @PostMapping("insert_pay")
    public ResponseEntity<?> insert_pay(@RequestBody Pay pay) {
        pay.setTime(new Date());
        return payServer.insertPay(pay);
    }

    @PostMapping("update_pay")
    public ResponseEntity<?> update_pay(@RequestParam("file") MultipartFile file,@RequestParam("id") Integer id,@RequestParam("pay") String pay,@RequestParam("description") String description,@RequestParam("who") String who) {
        return payServer.updateImage(file, id, pay, description, who);
    }

    @PostMapping("delete_by_id")
    public ResponseEntity<?> delete_by_id(@RequestBody Pay pay) {
        return payServer.deleteByID(pay.getId());
    }
}
