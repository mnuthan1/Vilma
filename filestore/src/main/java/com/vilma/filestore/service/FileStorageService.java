package com.vilma.filestore.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.vilma.filestore.entity.File;
import com.vilma.filestore.entity.Version;
import com.vilma.filestore.exceptions.FileStorageException;
import com.vilma.filestore.exceptions.InvalidFileException;
import com.vilma.filestore.exceptions.InvalidFileVersionException;
import com.vilma.filestore.exceptions.MyFileNotFoundException;
import com.vilma.filestore.repo.FileRepository;
import com.vilma.filestore.repo.VersionRepository;
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

    @Autowired
    private VersionRepository verRepo;

    /**
     * <p>
     * Method stores file in filestore
     * if the file id is provided then it will create new version of the
     * exiting file
     * </p>
     * 
     * @param id Optional existing file id
     * @param file MultipartFile to store
     * @return File object with id
     * @throws
     * InvalidFileException
     *      if the file name contains invalid path sequence ..
     * FileStorageException 
     *      Error while storing file on file store
     * 
     * @since 1.0
     */
    @Transactional
    public File storeFile(Optional<String> id, MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        File fileObj = null;
        Version latest = null;
        Version current = new Version();
        /* create a new version if the id presents */
        if(id.isPresent())
        {
            fileObj = fileRepo.findById(UUID.fromString(id.get()));
            if(!fileObj.getName().equals(fileName))
            {
                throw new InvalidFileException(
                    String.format(
                        "Original file name %s is different from current file name %s",fileObj.getName(),fileName
                    )
                );
            }
            latest = verRepo.findLatestVersionsByFileId(UUID.fromString(id.get()));
            current.setVer(latest.getVer()+1);
        } else {
            fileObj = new File();
            fileObj.setName(fileName);
            fileObj = fileRepo.save(fileObj);
        }
        current.setFileId(fileObj.getId());
        logger.debug("Uploading file:" + fileName);
        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new InvalidFileException("Sorry! Filename contains invalid path sequence " + fileName);
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
            String hashName = UUID.randomUUID().toString().replace("-", "");
            Files.copy(file.getInputStream(), targetLocation.resolve(hashName), StandardCopyOption.REPLACE_EXISTING);
            current.setPath(targetLocation.toUri());
            current.setHashName(hashName);
            current.setSize(file.getSize());
            current.setChecksum(FileUtil.getMd5Checksum(file));
            logger.debug("Saving new version:" + fileName);
            current = verRepo.save(current);
            /* create a new version if the id presents */
            if(id.isPresent())
            {
                logger.debug("Updating old version:" + fileName);
                latest.setNextVerId(current.getId());
                verRepo.save(latest);
            }
            return fileObj;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
    /**
     * <p>
     * Method to retrieve all version of a file
     * if the file id is provided then it will create new version of the
     * exiting file
     * </p>
     * 
     * @param id file id
     * @return List<Version> object with id
     * @throws
     * InvalidFileException
     * 
     * @since 1.0
     */
    public List<Version> getVersions(String id) {
        return verRepo.findAllVersionsByFileId(UUID.fromString(id));
    }
    public Resource loadFileAsResource(String id) throws IOException {
        return loadFileAsResource(id, -1);
    }

    public Resource loadFileAsResource(String id, int ver) throws IOException {

        File fileObj = fileRepo.findById(UUID.fromString(id));
        if(fileObj == null) {
            throw new MyFileNotFoundException("File not found " + id);
        }
        logger.debug("loading latest version:");
        Version latest = (ver == -1) ? verRepo.findLatestVersionsByFileId(UUID.fromString(id)): verRepo.findVersionByFileId(UUID.fromString(id), ver);
        if(latest == null) {
            throw new InvalidFileVersionException(String.format("Invalid file %s version %d",fileObj.getName(),ver));
        }
        try {
            URI uri = latest.getPath().resolve(latest.getHashName());
            Resource resource = new UrlResource(uri);
            /* create copy of file with original name */
            Path tempPath = Paths.get(this.fileStorageLocation +"/tmp/" + fileObj.getName());
            if(!latest.getChecksum().equals(FileUtil.getMd5Checksum(resource.getInputStream())))
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