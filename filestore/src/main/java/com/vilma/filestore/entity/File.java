/**
Entity class to represent File object
@author Nuthan Kumar (mnuthan@gmail.com)
*/
package com.vilma.filestore.entity;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
public class File {
    /** File object uniq ID */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "uuid", nullable = false)
    private UUID uuid;

    /** File name */
    @Column(name = "name", nullable = false)
    private String name;

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

    /**
     * versions - Transient to avoid data base persistence
     */
    @Transient
    private List<Version> versions;
}