package com.vilma.filestore.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.mock.web.MockMultipartFile;
/**
* Test class from FileUtil
* 
* @author Nuthan Kumar
* 
*/

public class FileUtilTest {
    /**
     * <p>Tests for bucket value with same file name
     * Generates same bucket for a given file name every time
     * </p>
     */
    @Test
    public void testFileHashWithSameName() {

        FileUtil.findBucket("test.java");
        assertEquals("Hash for Same file is not equal", FileUtil.findBucket("test.java"),
                FileUtil.findBucket("test.java"));
    }
    /**
     * <p>Tests for bucket value with different file names
     * </p>
     */
    @Test
    public void testFileHashWithDiffName() {

        FileUtil.findBucket("test.java");
        assertNotEquals("Hash for different files is same", FileUtil.findBucket("test.java"),
                FileUtil.findBucket("test1.java"));
    }

    /**
     * <p>
     * Tests for checksum util method
     * </p>
     * 
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    @Test
    public void testFileChecksum() throws NoSuchAlgorithmException, IOException {

        MultipartFile simpleFile = new MockMultipartFile("fileThatDoesNotExists.txt", "fileThatDoesNotExists.txt",
        "text/plain", "This is a dummy file content".getBytes(StandardCharsets.UTF_8));
        assertEquals("2136EF28FC423F94A7E5E5636B182C6C", FileUtil.getMd5Checksum(simpleFile).toUpperCase());
    }

    /**
     * <p>
     * Tests for checksum util method
     * </p>
     * 
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    @Test
    public void testStreamChecksum() throws NoSuchAlgorithmException, IOException {

        InputStream targetStream = new ByteArrayInputStream("This is a dummy file content".getBytes(StandardCharsets.UTF_8));
        assertEquals("2136EF28FC423F94A7E5E5636B182C6C", FileUtil.getMd5Checksum(targetStream).toUpperCase());
    }


}