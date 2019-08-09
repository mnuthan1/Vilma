/**
Entity class to represent File object
@author Nuthan Kumar (mnuthan@gmail.com)
*/
package com.vilma.filestore.entity;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Data;

@Entity
@Data
public class File {
    /** File object uniq ID */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    /** File name */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * created user
     */
    @Column(name = "created_by")
    private String createdBy;

    /**
     * last modified user
     */
    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    /**
     * created date
     */
    @CreatedDate
    @Column(name = "created_date")
    private Date crreatedDate;

    /**
     * last modified date
     */
    @LastModifiedDate
    @Column(name = "last_modified_date")
    private Date lastModifiedDate;

    /**
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        // TODO update with actual user
    }

    /**
     * @param lastModifiedBy the lastModifiedBy to set
     */
    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        // TODO update with actual user
    }
}