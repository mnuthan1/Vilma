package com.vilma.core.repo;

import java.util.UUID;

import com.vilma.core.entity.Attribute;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AttributeRepository extends JpaRepository<Attribute, Integer> {
    Attribute findByUuid(UUID id);

}