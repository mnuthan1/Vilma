package com.vilma.filestore.controller;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.vilma.filestore.entity.File;
import com.vilma.filestore.service.FileStorageService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    public File uploadFile(@RequestParam("file") MultipartFile file) throws NoSuchAlgorithmException {
        logger.info("Uploading File");
        return fileStorageService.storeFile(file);
    }

    @PostMapping(value= {"/files"})
    public List<File> uploadFiles(@PathVariable String id, @RequestParam("files") MultipartFile[] files) throws NoSuchAlgorithmException {
        return Arrays.asList(files)
                .stream()
                .map(file -> {
                    try {
                        return uploadFile(file);
                    } catch (NoSuchAlgorithmException e) {
                        logger.error("Error While uploading file %s:%s", file.getName(), e.getMessage());
                    }
                    return null;
                })
                .collect(Collectors.toList());
    }
}