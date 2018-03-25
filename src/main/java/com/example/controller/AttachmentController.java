package com.example.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.service.DriveFileService;
import com.example.service.PageService;
import com.example.storage.DriveService;

@Controller
public class AttachmentController {

	@Autowired
	private DriveService driveService;

	@Autowired
	private DriveFileService driveFileService;

	@Autowired
	private PageService pageService;

	@GetMapping("/attachments/{fileId}")
	public void getDownloadFile(HttpServletRequest request, HttpServletResponse response, @PathVariable String fileId)
			throws IOException {
		driveService.downloadFile(fileId, response);
	}

	@GetMapping("/admin/pages/{slug}/rm-att/{fileId}")
	public String getRemoveAttachment(@PathVariable String slug, @PathVariable String fileId) {
		driveFileService.deleteByPageIdAndFileId(pageService.findBySlug(slug).getId(), fileId);
		return "redirect:/admin/pages/" + slug;
	}
}
