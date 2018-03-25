package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.DriveFile;
import com.example.repository.DriveFileRepository;

@Service
public class DriveFileServiceImpl implements DriveFileService {

	@Autowired
	private DriveFileRepository driveFileRepository;

	@Override
	public void save(DriveFile drive) {
		driveFileRepository.save(drive);
	}

	@Override
	public void deleteById(int id) {
		driveFileRepository.deleteById(id);

	}

	@Override
	public void deleteByPageIdAndFileId(int pageId, String fileId) {
		driveFileRepository.deleteById(driveFileRepository.findByPageIdAndFileId(pageId, fileId).getId());
	}

}
