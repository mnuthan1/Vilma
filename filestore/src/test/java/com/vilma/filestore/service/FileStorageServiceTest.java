package com.vilma.filestore.service;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;

import com.vilma.filestore.AbstractTest;
import com.vilma.filestore.entity.File;
import com.vilma.filestore.repo.FileRepository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class FileStorageServiceTest extends AbstractTest {

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    FileRepository fileRepo;

    @Test
    public void testFileStore() throws NoSuchAlgorithmException, IOException {
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
    }

    @Test
    public void testFileRetrieve() throws NoSuchAlgorithmException, IOException {
        String fileContent = "This is a dummy file content\n new line";
        String fileName = "fileThatDoesNotExists.txt";
        MultipartFile simpleFile = new MockMultipartFile(fileName,fileName,
                "text/plain", fileContent.getBytes(StandardCharsets.UTF_8));
                File file = null;
        try {
            file = fileStorageService.storeFile(simpleFile);
        } catch(Exception e)
        {

        }
        Resource res = fileStorageService.loadFileAsResource(file.getId().toString());
        Charset charset = Charset.forName("US-ASCII");
        StringBuffer results = new StringBuffer();
        try (BufferedReader reader = Files.newBufferedReader(res.getFile().toPath(), charset)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                if(!results.toString().isEmpty())
                    results.append("\n");
                results.append(line);
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
        assertEquals(fileContent, results.toString());
        assertEquals(res.getFile().getName(),fileName);
    }
}