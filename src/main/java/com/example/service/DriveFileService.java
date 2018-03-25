package com.example.service;

import com.example.model.DriveFile;

public interface DriveFileService {
	void save(DriveFile drive);

	void deleteById(int id);

	void deleteByPageIdAndFileId(int pageId, String fileId);
}
