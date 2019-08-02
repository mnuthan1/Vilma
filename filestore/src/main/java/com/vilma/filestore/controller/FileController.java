package com.vilma.filestore.controller;

import java.io.IOException;
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
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
    
    @GetMapping(value= {"/file/{id}"})
    public ResponseEntity<Resource> downloadFile(@PathVariable String id, HttpServletRequest request)
            throws IOException {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(id);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    //TODO include application name in the input 
    @PostMapping("/file")
    public File uploadFile(@RequestParam("file") MultipartFile file) throws NoSuchAlgorithmException {
        logger.info("Uploading File");
        return fileStorageService.storeFile(file);
    }

    @PostMapping(value= {"/files"},consumes = { "multipart/form-data" })
    public List<File> uploadFiles(@RequestParam("files") MultipartFile[] files) throws NoSuchAlgorithmException {
        return Arrays.asList(files)
                .stream()
                .map(file -> {
                    try {
                        return uploadFile(file);
                    } catch (NoSuchAlgorithmException e) {
                        logger.error("Error While uploading file %s:%s", file.getName(), e.getMessage());
                        return null;
                        //TODO return proper error message in the file
                    }
                    
                })
                .collect(Collectors.toList());
    }
}