package com.vilma.filestore.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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

    @GetMapping(value = { "/file/{id}", "file/{id}/revision/latest" })
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
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    // TODO include application name in the input
    @PostMapping(value= {"/file", "/file/{id}"} )
    public File uploadFile(@PathVariable Optional<String> id, @RequestParam("file") MultipartFile file) {
        logger.info("Uploading File");
        return fileStorageService.storeFile(id, file);
    }

    @PostMapping(value = { "/files" }, consumes = { "multipart/form-data" })
    public List<File> uploadFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files).stream().map(file -> {
            return fileStorageService.storeFile(Optional.ofNullable(null), file);
        }).collect(Collectors.toList());
    }
}