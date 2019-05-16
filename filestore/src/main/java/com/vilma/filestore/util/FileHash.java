package com.vilma.filestore.util;

public class FileHash {
    
    public static String calculateHash(String filename) {
        int hashcode = filename.hashCode();
        int mask = 255;
        String firstLevelDirectory = Integer.toString(hashcode &  mask);
        String secondLevelDirectory = Integer.toString((hashcode >> 8)&  mask);
        return "/"+firstLevelDirectory+"/"+secondLevelDirectory;
    }

}