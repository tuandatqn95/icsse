package com.example.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.model.Page;
import com.example.service.PageService;
import com.example.service.PostService;

@Controller
public class PageController {

	@Autowired
	private PageService pageService;

	@Autowired
	private PostService postService;

	@GetMapping("/")
	public String Home(Model model) {
		model.addAttribute("page", pageService.findBySlug("home"));
		model.addAttribute("lastestposts", postService.getTop5Lastest());
		return "single-page";
	}

	@GetMapping("/{slug}")
	public String getPageBySlug(@PathVariable String slug, Model model) {
		model.addAttribute("page", pageService.findBySlug(slug));
		return "single-page";

	}

	@GetMapping("/admin/pages/{slug}")
	public String getEditPageBySlug(@PathVariable String slug, Model model) {
		model.addAttribute("page", pageService.findBySlug(slug));
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
