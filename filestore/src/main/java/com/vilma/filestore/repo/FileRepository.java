package com.vilma.filestore.repo;

import java.util.UUID;

import com.vilma.filestore.entity.File;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Integer> {
    File findById(UUID id);
}