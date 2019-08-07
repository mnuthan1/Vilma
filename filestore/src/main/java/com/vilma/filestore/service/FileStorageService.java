package com.vilma.filestore.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import com.vilma.filestore.entity.File;
import com.vilma.filestore.exceptions.FileStorageException;
import com.vilma.filestore.exceptions.InvalidFileException;
import com.vilma.filestore.exceptions.MyFileNotFoundException;
import com.vilma.filestore.repo.FileRepository;
import com.vilma.filestore.util.FileUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {
    public static final Logger logger = LoggerFactory.getLogger(FileStorageService.class);
    private final Path fileStorageLocation;

    @Value("${filestore.baseUrl}")
    private String baseUrl;

    @Autowired
    public FileStorageService(@Value("${filestore.baseUrl}") final String baseUrl) {
        this.fileStorageLocation = Paths.get(baseUrl).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.",
                    ex);
        }
    }

    @Autowired
    private FileRepository fileRepo;

    @Transactional
    public File storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        File fileObj = new File();
        fileObj.setName(fileName);
        fileObj = fileRepo.save(fileObj);
        logger.debug("Uploading file:" + fileName);
        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            // Copy file to the target location (Replacing existing file with the same name)
            String bucket = FileUtil.findBucket(fileName);
            Path targetLocation = Paths.get(baseUrl + bucket).toAbsolutePath().normalize();
            try {
                Files.createDirectories(targetLocation);
            } catch (Exception ex) {
                throw new FileStorageException(
                        "Could not create the directory where the uploaded files will be stored.", ex);
            }

            Files.copy(file.getInputStream(), targetLocation.resolve(fileObj.getId().toString()), StandardCopyOption.REPLACE_EXISTING);
            fileObj.setPath(targetLocation.toUri());
            fileObj.setSize(file.getSize());
            fileObj.setCehcksum(FileUtil.getMd5Checksum(file));
            return fileRepo.save(fileObj);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String id) throws IOException {
        File fileObj = fileRepo.findById(UUID.fromString(id));
        try {
            
            URI uri = fileObj.getPath().resolve(fileObj.getId().toString());
            Resource resource = new UrlResource(uri);
            /* create copy of file with original name */
            Path tempPath = Paths.get(this.fileStorageLocation +"/tmp/" + fileObj.getName());
            if(!fileObj.getCehcksum().equals(FileUtil.getMd5Checksum(resource.getInputStream())))
            {
                throw new InvalidFileException(String.format("Error while verifying checksum on %s",fileObj.getName()));
            } 
            Files.copy(resource.getInputStream(), tempPath, StandardCopyOption.REPLACE_EXISTING);
            uri = tempPath.toUri();
            resource = new UrlResource(uri);
            if (resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileObj.getName());
            }
        } catch (MalformedURLException ex) {
            throw new FileStorageException(String.format("Error While retrieve file %s:%s", fileObj.getName(),ex.getMessage()));
        }
    }
}