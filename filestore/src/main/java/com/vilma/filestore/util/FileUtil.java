package com.vilma.filestore.util;

import java.io.IOException;

import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * FileUtils contains static utility methods for file handling
 * 
 * @author Nuthan Kumar
 * 
 */
public class FileUtil {
    /**
     * <p>
     * Method identifies bucket name in the file store for given file name Generates
     * same bucket for a given file name every time
     * </p>
     * 
     * @param filename input file name
     * @return bucket details (2 level folder structure in file store)
     * @since 1.0
     */
    public static String findBucket(String filename) {
        int hashcode = filename.hashCode();
        int mask = 255;
        String firstLevelDirectory = Integer.toString(hashcode & mask);
        String secondLevelDirectory = Integer.toString((hashcode >> 8) & mask);
        return "/" + firstLevelDirectory + "/" + secondLevelDirectory;
    }

    /**
     * <p>
     * Method to create MD5 checksum for given MultipartFile
     * </p>
     * 
     * @param file MultipartFile Object
     * @return String md5 checksum
     * @throws IOException
     * @since 1.0
     */
    public static String getMd5Checksum(MultipartFile file) throws IOException {
        return DigestUtils.md5DigestAsHex(file.getInputStream());
    }

}