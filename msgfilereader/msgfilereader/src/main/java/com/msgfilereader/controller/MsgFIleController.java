package com.msgfilereader.controller;

import com.msgfilereader.service.MsgFileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;



@RestController
@RequestMapping("/api/msg")
public class MsgFIleController{

    private final MsgFileService msgFileService;

    public MsgFIleController(MsgFileService msgFileService) {
        this.msgFileService = msgFileService;
    }
    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadMsgFile(@RequestParam("file") MultipartFile file) throws  IOException {
        Map<String, Object> emailData = msgFileService.processMsgFile(file);
        return ResponseEntity.ok(emailData);
    }

}
