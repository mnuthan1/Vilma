/**
 * Hibernate Entity class for Attribute table
 * @author Nuthan Kumar (mnuthan@gmail.com)
 */
package com.vilma.core.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name="meta_attribute")
@Data
@EqualsAndHashCode(callSuper = false)
@TypeDefs({
    @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class),
})

public class Attribute extends BaseModel {

    private static final long serialVersionUID = 8795814881233242806L;

    /** attribute object uniq ID */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "uuid", nullable = false)
    private UUID uuid;

    /** File name */
    @Column(name = "name", nullable = false)
    private String name;

    /** class name  default attribute*/
    @Column(name = "class_type")
    private String classType;

    /** class name  default attribute*/
    @Column(name = "attr_type", nullable = false)
    private String attrType;

    
    /** is array type*/
    @Column(name = "is_array")
    private Boolean isArray;

    /** format to display values*/
    @Column(name = "format")
    private String format;

    @Type(type = "jsonb")
    @Column(name = "range_def", columnDefinition = "jsonb")
    private AttributeRangeDef<String> rangeDef;

    @Type(type = "jsonb")
    @Column(name = "table_def", columnDefinition = "jsonb")
    private AttributeTableDef tableDef;

    @Type(type = "jsonb")
    @Column(name = "graph_def", columnDefinition = "jsonb")
    private AttributeGraphDef graphDef;

}