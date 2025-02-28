package com.orders.translate.constrollers;

import com.orders.translate.services.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.logging.Logger;

@Controller
@RequestMapping("/api/v1/translate")
public class UploadFileController {
    private final Logger logger = Logger.getLogger(UploadFileController.class.getName());

    @Autowired
    private final UploadFileService uploadFileService;

    public UploadFileController(UploadFileService uploadFileService) {
        this.uploadFileService = uploadFileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> fileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        logger.info("Processing file");

        String fileContent = String.valueOf(uploadFileService.processFile(file));
        return ResponseEntity.ok("File processed successfully");
    }
}
