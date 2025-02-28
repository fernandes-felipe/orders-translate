package com.orders.translate.services;

import com.orders.translate.constrollers.UploadFileController;
import com.orders.translate.constrollers.dto.UploadFileResponseDTO;
import com.orders.translate.exceptions.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.logging.Logger;

@Service
public class UploadFileService {

    @Autowired
    private final TranslateService translateService;

    private final Logger logger = Logger.getLogger(UploadFileService.class.getName());

    public UploadFileService(TranslateService translateService) {
        this.translateService = translateService;
    }

    public UploadFileResponseDTO processFile(MultipartFile file) throws IOException {

        try {
            logger.info("[FileService.processFile] Opening file");

            String fileName = getFileName(file);
            String fileContentToString = openFile(file);

            if((fileName != null && fileName.contains("..")) ||(!Objects.requireNonNull(fileName).contains("txt")) ) {
                throw new FileUploadException("Sorry! Filename contains invalid format" + fileName);
            }else {
                logger.info("File format ok!");
                translateService.translateFile(fileContentToString);
                return new UploadFileResponseDTO(fileName, file.getContentType());
            }
        } catch (Exception e) {
            throw new FileUploadException("Could not process file. Please try again!", e);
        }
    }

    private String openFile (MultipartFile file) throws IOException {
        try {
            InputStream inputStream = file.getInputStream();
            byte[] fileContent = inputStream.readAllBytes();
            return new String(fileContent);
        }catch (Exception e) {
            throw new FileUploadException("Could not process file. Please try again!", e);
        }
    }

    private String getFileName (MultipartFile file) {
        return Objects.requireNonNull(file.getOriginalFilename());
    }
}
