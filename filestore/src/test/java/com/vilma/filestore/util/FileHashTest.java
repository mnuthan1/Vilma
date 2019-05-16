package com.vilma.filestore.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import org.junit.Test;

public class FileHashTest {
    @Test
    public void testFileHashWithSameName() {

        FileHash.calculateHash("test.java");
        assertEquals("Hash for Same file is not equal", FileHash.calculateHash("test.java"),
                FileHash.calculateHash("test.java"));
    }

    @Test
    public void testFileHashWithDiffName() {

        FileHash.calculateHash("test.java");
        assertNotEquals("Hash for different files is same", FileHash.calculateHash("test.java"),
                FileHash.calculateHash("test1.java"));
    }

}