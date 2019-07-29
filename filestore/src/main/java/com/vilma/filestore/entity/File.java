package com.vilma.filestore.entity;

import java.net.URI;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class File {
    /** File object uniq ID */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    /** File store path */
    private URI path;
    /** File name */
    private String name;
    /** File size in bytes*/
    private long size;
    /** MD5 cehcksum */
    private String cehcksum;

    /**
     * @param path the path to set
     */
    public void setPath(URI path) {
        this.path = path;
    }

    /**
     * @return the path
     */
    public URI getPath() {
        return path;
    }

    /**
     * @param id the id to set
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * @return the id
     */
    public UUID getId() {
        return id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param size the size to set
     */
    public void setSize(long size) {
        this.size = size;
    }

    /**
     * @return the size
     */
    public long getSize() {
        return size;
    }

    /**
     * @param cehcksum the cehcksum to set
     */
    public void setCehcksum(String cehcksum) {
        this.cehcksum = cehcksum;
    }

    /**
     * @return the cehcksum
     */
    public String getCehcksum() {
        return cehcksum;
    }

    @Override
    public String toString() {
        return String.format("Name: %s, ID: %s", name, id);
    }

}