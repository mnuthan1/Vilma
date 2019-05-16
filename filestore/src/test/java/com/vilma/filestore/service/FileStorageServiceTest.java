package com.vilma.filestore.service;

import static org.junit.Assert.assertEquals;

import java.nio.charset.StandardCharsets;

import com.vilma.filestore.AbstractTest;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class FileStorageServiceTest extends AbstractTest {

    @Autowired
    FileStorageService fileStorageService;

    @Test
    public void testFileStore() {
        MultipartFile fichier = new MockMultipartFile("fileThatDoesNotExists.txt", "fileThatDoesNotExists.txt",
                "text/plain", "This is a dummy file content".getBytes(StandardCharsets.UTF_8));

        String path = fileStorageService.storeFile(fichier);
        assertEquals(path, "fileThatDoesNotExists.txt");
    }
}