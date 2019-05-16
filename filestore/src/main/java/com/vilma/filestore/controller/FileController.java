package com.vilma.filestore.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.vilma.filestore.entity.UploadResponse;
import com.vilma.filestore.service.FileStorageService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class FileController {
    
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    

    @Autowired
    private FileStorageService fileStorageService;
    
    @GetMapping(value= {"/file/{id}","/file"})
    public ResponseEntity<?> downloadFile(@PathVariable String id, HttpServletRequest request) {
        return null;
    }

    @PostMapping("/file")
    public UploadResponse uploadFile(@RequestParam("file") MultipartFile file) {
        logger.debug("Uploading File");
        String fileName = fileStorageService.storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        return new UploadResponse(fileName, fileDownloadUri, file.getSize());
    }

    @PostMapping(value= {"/files"})
    public List<UploadResponse> uploadFiles(@PathVariable String id, @RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }
}