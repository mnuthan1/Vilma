package com.vilma.filestore.service;

import static org.junit.Assert.assertEquals;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.vilma.filestore.AbstractTest;
import com.vilma.filestore.entity.File;
import com.vilma.filestore.repo.FileRepository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class FileStorageServiceTest extends AbstractTest {

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    FileRepository fileRepo;

    @Test
    public void testFileStoreService() throws NoSuchAlgorithmException {
        MultipartFile simpleFile = new MockMultipartFile("fileThatDoesNotExists.txt", "fileThatDoesNotExists.txt",
                "text/plain", "This is a dummy file content".getBytes(StandardCharsets.UTF_8));
                File file = null;
        try {
            file = fileStorageService.storeFile(simpleFile);
        } catch(Exception e)
        {

        }
        
        assertEquals(28,file.getSize());
        assertEquals("2136EF28FC423F94A7E5E5636B182C6C", file.getCehcksum().toUpperCase());
        List<File> fl = fileRepo.findAll();
        fileRepo.findAll().forEach(f -> System.out.println(f.getName()) );
    }
}