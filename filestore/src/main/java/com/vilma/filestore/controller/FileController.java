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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1")
@Api(value="File upload management service", description="Operations pertaining to file storage")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileStorageService fileStorageService;

    @ApiOperation(value = "Gets latest version of given file id")
    @GetMapping(value = { "/file/{id}", "file/{id}/revision/latest" })
    public ResponseEntity<Resource> downloadFile(@PathVariable String id, HttpServletRequest request)
            throws IOException {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(id);
        return generateFileResponse(resource, request);
    }

    @ApiOperation(value = "Gets a specific version of given file id")
    @GetMapping(value = { "file/{id}/revision/{ver}" })
    public ResponseEntity<Resource> downloadFileVersion(@PathVariable String id,@PathVariable int ver, HttpServletRequest request)
            throws IOException {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(id, ver);
        return generateFileResponse(resource, request);
    }

    // TODO include application name in the input
    @ApiOperation(value = "created a new file with version 1")
    @PostMapping(value= {"/file"} )
    public File uploadFile( @RequestParam("file") MultipartFile file) {
        logger.info("Creating new file");
        return fileStorageService.storeFile(Optional.ofNullable(null), file);
    }

    @ApiOperation(value = "created a new version of a file")
    @PostMapping(value= {"/file/{id}"} )
    public File uploadFileVersion(@PathVariable String id, @RequestParam("file") MultipartFile file) {
        logger.info("Creating new version File");
        return fileStorageService.storeFile(Optional.ofNullable(id), file);
    }

    @PostMapping(value = { "/files" }, consumes = { "multipart/form-data" })
    public List<File> uploadFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files).stream().map(file -> {
            return fileStorageService.storeFile(Optional.ofNullable(null), file);
        }).collect(Collectors.toList());
    }

    private ResponseEntity<Resource> generateFileResponse(Resource resource, HttpServletRequest request) {
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
}