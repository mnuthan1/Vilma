package com.vilma.filestore.repo;

import java.util.List;
import java.util.UUID;

import com.vilma.filestore.entity.Version;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VersionRepository extends JpaRepository<Version, Integer> {
    /**
     * finds latest version object for given file id
     * @param UUID file id
     * @return Version latest Version object
     */
    @Query("SELECT v FROM Version v WHERE v.fileId = ?1 and v.nextVerId is null")
    Version findLatestVersionsByFileId(UUID id);
    /**
     * finds all version object for given file id
     * @param UUID file id
     * @return List<Version> list of verion objects
     */
    @Query("SELECT v FROM Version v WHERE v.fileId = ?1")
    List<Version> findAllVersionsByFileId(UUID id);

    /**
     * finds a version object for given file id
     * @param UUID file id
     * @param int version number
     * @return List<Version> list of verion objects
     */
    @Query("SELECT v FROM Version v WHERE v.fileId = ?1 and v.ver = ?2")
    Version findVersionByFileId(UUID id, int version);

}