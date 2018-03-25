package com.example.controller;

import java.io.IOException;
import java.nio.file.Path;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.model.DriveFile;
import com.example.model.Page;
import com.example.service.DriveFileService;
import com.example.service.PageService;
import com.example.storage.DriveService;
import com.example.storage.StorageService;
import com.google.api.services.drive.model.File;

@Controller
public class PageController {

	@Autowired
	private PageService pageService;

	@Autowired
	private StorageService storageService;

	@Autowired
	private DriveService driveService;

	@Autowired
	private DriveFileService driveFileService;

	@GetMapping("/")
	public String Home(Model model) throws IOException {
		model.addAttribute("page", pageService.findBySlug("home"));
		model.addAttribute("attachments",
				driveService.getListDriveFile(pageService.findBySlug("home").getDriveFiles()));
		return "single-page";
	}

	@GetMapping("/{slug}")
	public String getPageBySlug(@PathVariable String slug, Model model) throws IOException {
		model.addAttribute("page", pageService.findBySlug(slug));
		return "single-page";
	}

	@GetMapping("/admin/pages/{slug}")
	public String getEditPageBySlug(@PathVariable String slug, Model model) throws IOException {
		model.addAttribute("page", pageService.findBySlug(slug));
		model.addAttribute("attachments", driveService.getListDriveFile(pageService.findBySlug(slug).getDriveFiles()));
		return "admin/page-editor";
	}

	@PostMapping("/admin/pages/{slug}")
	public String postPageBySlug(@PathVariable String slug, @RequestParam("file") MultipartFile file, Model model)
			throws IOException {
		Page page = pageService.findBySlug(slug);
		Path filePath = storageService.store(file);
		File driveFile = driveService.uploadFile(filePath, false);
		DriveFile driveInfo = new DriveFile(page.getId(), driveFile.getId());
		driveFileService.save(driveInfo);
		model.addAttribute("page", page);
		model.addAttribute("attachments", driveService.getListDriveFile(pageService.findBySlug(slug).getDriveFiles()));
		return "admin/page-editor";
	}

	@PostMapping("/admin/pages")
	public String postPage(@Valid Page page, BindingResult result, RedirectAttributes redirect) {
		if (result.hasErrors()) {
			redirect.addFlashAttribute("page", page);
			redirect.addFlashAttribute("error", "Can not update page!");
		} else {
			pageService.save(page);
			redirect.addFlashAttribute("message", "Update page successfully!");
		}
		return "redirect:/admin/pages/" + page.getSlug();
	}

}
