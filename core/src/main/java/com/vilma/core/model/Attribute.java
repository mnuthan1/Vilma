/**
 * Hibernate Entity class for Attribute table
 * @author Nuthan Kumar (mnuthan@gmail.com)
 */
package com.vilma.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="meta_attritbue")
@Data
public class Attribute extends BaseModel {

    private static final long serialVersionUID = 8795814881233242806L;

    /** attribute object uniq ID */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "uuid", nullable = false)
    private UUID uuid;

}