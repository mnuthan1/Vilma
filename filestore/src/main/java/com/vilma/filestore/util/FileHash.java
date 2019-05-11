
public class FileHash {
    private String directory;
    private String hashedFileName;
    private String filename;
    /**
     * Constuctor
     * @param filename
     */
    public FileHash(String filename)
    {
        this.filename = filename;
    }
    

    public void calculateHash() {
        int hashcode = this.filename.hashCode();
        int mask = 255;
        String firstLevelDirectory = Integer.toString(hashcode &  mask);
        String secondLevelDirectory = Integer.toString((hashcode >> 8)&  mask);
        this.directory = "/"+firstLevelDirectory+"/"+secondLevelDirectory;
    }

    /**
     * @return the directory
     */
    public String getDirectory() {
        return directory;
    }
    /**
     * @return the hashedFileName
     */
    public String getHashedFileName() {
        return hashedFileName;
    }

}