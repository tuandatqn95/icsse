package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.DriveFile;

@Repository
public interface DriveFileRepository extends JpaRepository<DriveFile, Integer> {

	void deleteByPageIdAndFileId(int pageId, String fileId);

	DriveFile findByPageIdAndFileId(int pageId, String fileId);
}
