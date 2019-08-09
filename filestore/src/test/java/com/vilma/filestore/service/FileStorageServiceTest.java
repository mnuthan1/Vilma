package com.vilma.filestore.service;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.UUID;

import com.vilma.filestore.AbstractTest;
import com.vilma.filestore.entity.File;
import com.vilma.filestore.entity.Version;
import com.vilma.filestore.exceptions.InvalidFileException;
import com.vilma.filestore.exceptions.InvalidFileVersionException;
import com.vilma.filestore.exceptions.MyFileNotFoundException;
import com.vilma.filestore.repo.FileRepository;
import com.vilma.filestore.repo.VersionRepository;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;


public class FileStorageServiceTest extends AbstractTest {

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    FileRepository fileRepo;

    @Autowired
    VersionRepository verRepo;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void testFileStoreNewFileInvalidName() {
        MultipartFile simpleFile = new MockMultipartFile("fileThatDoesNotExists..txt", "fileThatDoesNotExists..txt",
                "text/plain", "This is a dummy file content".getBytes(StandardCharsets.UTF_8));
        expectedEx.expect(InvalidFileException.class);
        expectedEx.expectMessage("Sorry! Filename contains invalid path sequence fileThatDoesNotExists..txt");
        fileStorageService.storeFile(Optional.ofNullable(null), simpleFile);
    }

    @Test
    public void testFileStoreNewFile() throws NoSuchAlgorithmException, IOException {
        MultipartFile simpleFile = new MockMultipartFile("fileThatDoesNotExists.txt", "fileThatDoesNotExists.txt",
                "text/plain", "This is a dummy file content".getBytes(StandardCharsets.UTF_8));
        File file = null;
        file = fileStorageService.storeFile(Optional.ofNullable(null), simpleFile);
        Version ver = verRepo.findLatestVersionsByFileId(file.getId());
        assertEquals(28, ver.getSize());
        assertEquals("2136EF28FC423F94A7E5E5636B182C6C", ver.getCehcksum().toUpperCase());
    }

    @Test
    public void testFileStoreMultipleVersions() throws NoSuchAlgorithmException, IOException {
        MultipartFile simpleFile = new MockMultipartFile("fileThatDoesNotExists.txt", "fileThatDoesNotExists.txt",
                "text/plain", "This is a dummy file content".getBytes(StandardCharsets.UTF_8));
        File file = null;
        file = fileStorageService.storeFile(Optional.ofNullable(null), simpleFile);
        /* storing new version */
        simpleFile = new MockMultipartFile("fileThatDoesNotExists.txt", "fileThatDoesNotExists.txt", "text/plain",
                "This is a dummy file content with new version".getBytes(StandardCharsets.UTF_8));

        file = fileStorageService.storeFile(Optional.ofNullable(file.getId().toString()), simpleFile);

        Version ver = verRepo.findLatestVersionsByFileId(file.getId());
        assertEquals("Incorrect File size", 45, ver.getSize());
        assertEquals("Incorrect File checksum", "B5859B4D5A1A34A9F3877A586F072047", ver.getCehcksum().toUpperCase());
        assertEquals("Invalid number of versions", 2, fileStorageService.getVersions(file.getId().toString()).size());
    }

    @Test
    public void testFileStoreMultipleVersionsDiffName() throws NoSuchAlgorithmException, IOException {
        MultipartFile simpleFile = new MockMultipartFile("fileThatDoesNotExists.txt", "fileThatDoesNotExists.txt",
                "text/plain", "This is a dummy file content".getBytes(StandardCharsets.UTF_8));
        File file = null;
        file = fileStorageService.storeFile(Optional.ofNullable(null), simpleFile);
        /* storing new version */
        simpleFile = new MockMultipartFile("fileThatDoesNotExists.txt", "fileThatDoesNotExists_new.txt", "text/plain",
                "This is a dummy file content with new version".getBytes(StandardCharsets.UTF_8));

        expectedEx.expect(InvalidFileException.class);
        expectedEx.expectMessage(
                "Original file name fileThatDoesNotExists.txt is different from current file name fileThatDoesNotExists_new.txt");
        file = fileStorageService.storeFile(Optional.ofNullable(file.getId().toString()), simpleFile);
    }

    @Test
    public void testFileRetrieveSingleVer() throws NoSuchAlgorithmException, IOException {
        String fileContent = "This is a dummy file content\n new line";
        String fileName = "fileThatDoesNotExists.txt";
        MultipartFile simpleFile = new MockMultipartFile(fileName, fileName, "text/plain",
                fileContent.getBytes(StandardCharsets.UTF_8));
        File file = null;
        file = fileStorageService.storeFile(Optional.ofNullable(null), simpleFile);

        Resource res = fileStorageService.loadFileAsResource(file.getId().toString());
        Charset charset = Charset.forName("US-ASCII");
        StringBuffer results = new StringBuffer();
        try (BufferedReader reader = Files.newBufferedReader(res.getFile().toPath(), charset)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (!results.toString().isEmpty())
                    results.append("\n");
                results.append(line);
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
        assertEquals(fileContent, results.toString());
        assertEquals(res.getFile().getName(), fileName);
    }

    @Test
    public void testFileRetrieveLatestVer() throws NoSuchAlgorithmException, IOException {
        String fileContent = "This is a dummy file content\n new line";
        String fileName = "fileThatDoesNotExists.txt";
        MultipartFile simpleFile = new MockMultipartFile(fileName, fileName, "text/plain",
                fileContent.getBytes(StandardCharsets.UTF_8));
        File file = null;
        file = fileStorageService.storeFile(Optional.ofNullable(null), simpleFile);

        fileContent = "This is a dummy file content\n new line with new ver";
        simpleFile = new MockMultipartFile(fileName, fileName, "text/plain",
                fileContent.getBytes(StandardCharsets.UTF_8));

        file = fileStorageService.storeFile(Optional.ofNullable(file.getId().toString()), simpleFile);

        Resource res = fileStorageService.loadFileAsResource(file.getId().toString());
        Charset charset = Charset.forName("US-ASCII");
        StringBuffer results = new StringBuffer();
        try (BufferedReader reader = Files.newBufferedReader(res.getFile().toPath(), charset)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (!results.toString().isEmpty())
                    results.append("\n");
                results.append(line);
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
        assertEquals(fileContent, results.toString());
        assertEquals(res.getFile().getName(), fileName);
    }

    @Test
    public void testFileRetrievePreVer() throws NoSuchAlgorithmException, IOException {
        String fileContent = "This is a dummy file content\n new line";
        String fileName = "fileThatDoesNotExists.txt";
        MultipartFile simpleFile = new MockMultipartFile(fileName, fileName, "text/plain",
                fileContent.getBytes(StandardCharsets.UTF_8));
        File file = null;
        file = fileStorageService.storeFile(Optional.ofNullable(null), simpleFile);

        String newFileContent = "This is a dummy file content\n new line with new ver";
        simpleFile = new MockMultipartFile(fileName, fileName, "text/plain",
                newFileContent.getBytes(StandardCharsets.UTF_8));

        file = fileStorageService.storeFile(Optional.ofNullable(file.getId().toString()), simpleFile);

        Resource res = fileStorageService.loadFileAsResource(file.getId().toString(), 0);
        Charset charset = Charset.forName("US-ASCII");
        StringBuffer results = new StringBuffer();
        try (BufferedReader reader = Files.newBufferedReader(res.getFile().toPath(), charset)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (!results.toString().isEmpty())
                    results.append("\n");
                results.append(line);
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
        assertEquals(fileContent, results.toString());
        assertEquals(res.getFile().getName(), fileName);
    }

    @Test
    public void testFileRetrieveInvalidVer() throws IOException {
        String fileContent = "This is a dummy file content\n new line";
        String fileName = "fileThatDoesNotExists.txt";
        MultipartFile simpleFile = new MockMultipartFile(fileName, fileName, "text/plain",
                fileContent.getBytes(StandardCharsets.UTF_8));
        File file = null;
        file = fileStorageService.storeFile(Optional.ofNullable(null), simpleFile);

        expectedEx.expect(InvalidFileVersionException.class);
        expectedEx.expectMessage("Invalid file fileThatDoesNotExists.txt version 2");
        fileStorageService.loadFileAsResource(file.getId().toString(), 2);
    }

    @Test
    public void testFileRetrieveInvalidFile() throws IOException {
        expectedEx.expect(MyFileNotFoundException.class);
        UUID temp = UUID.randomUUID();
        expectedEx.expectMessage("File not found " + temp.toString());
        fileStorageService.loadFileAsResource(temp.toString(), 2);
    }
}