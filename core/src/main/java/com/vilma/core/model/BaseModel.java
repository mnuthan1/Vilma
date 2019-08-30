/**
 * Hibernate Base class for holding common table properties
 * @author Nuthan Kumar (mnuthan@gmail.com)
 */
package com.vilma.core.model;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public abstract class BaseModel implements Serializable {

    private static final long serialVersionUID = 8706457846230207507L;

	/**
     * created user
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on", nullable = false, updatable = false)
    @CreatedDate
    private Date createdOn;

    /**
     * last modified user
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_modified_on", nullable = false)
    @LastModifiedDate
    private Date lastModifiedOn;

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

}