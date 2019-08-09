/**
Entity class to represent File version object
@author Nuthan Kumar (mnuthan@gmail.com)
*/
package com.vilma.filestore.entity;

import java.net.URI;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;
import lombok.ToString;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Version {
    /** File object uniq ID */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;
    
    /** File store path */
    @ToString.Exclude
    @Column(name = "path")
    private URI path;
    
    /** File id */
    @Column(name = "file_id", nullable = false)
    private UUID fileId;
    
    /** File size in bytes*/
    @Column(name = "size")
    private long size;
    
    /** MD5 checksum */
    @Column(name = "checksum", nullable = false)
    private String checksum;

    /** Version number */
    @Column(name = "ver", nullable = false)
    private int ver;

    /** Next version id */
    @Column(name = "next_ver_id")
    private UUID nextVerId;

    @Column(name = "hash_name", nullable = false)
    private String hashName;
    /**
     * created user
     */
    @Column(name = "created_by")
    @CreatedBy
    private String createdBy;

    /**
     * last modified user
     */
    @Column(name = "last_modified_by")
    @LastModifiedBy
    private String lastModifiedBy;

    /**
     * created date
     */
    @CreatedDate
    @Column(name = "created_date")
    private Date createdDate;

    /**
     * last modified date
     */
    @LastModifiedDate
    @Column(name = "last_modified_date")
    private Date lastModifiedDate;
}