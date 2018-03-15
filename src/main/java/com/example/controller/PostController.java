package com.example.controller;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.model.Post;
import com.example.service.PostService;
import com.example.service.UserService;

@Controller
public class PostController {

	@Autowired
	private PostService postService;
	@Autowired
	private UserService userService;

	@GetMapping("/lastest-post")
	public String getLastestPost() {

		return "list-post";
	}

	@GetMapping("/posts/{id}-{slug}")
	public String getSinglePost(@PathVariable int id, @PathVariable String slug, Model model) {
		model.addAttribute("post", postService.findById(id));
		return "single-post";
	}

	@GetMapping("/admin/posts")
	public String getListPostAdmin(Model model) {
		model.addAttribute("posts", postService.getListPost());
		return "admin/list-post";
	}

	@GetMapping("/admin/add-post")
	public String getFormAddPost(Model model) {
		model.addAttribute("post", new Post());
		return "admin/add-post";
	}

	@GetMapping("/admin/edit-post/{id}")
	public String getFormEditPost(@PathVariable int id, Model model) {
		model.addAttribute("post", postService.findById(id));
		return "admin/edit-post";
	}

	@PostMapping("/admin/posts")
	public String postSubmitPost(@Valid Post post, BindingResult result, RedirectAttributes redirect) {
		if (result.hasErrors()) {
			redirect.addAttribute("post", post);
			if (postService.idExist(post.getId())) {
				return "redirect:/admin/edit-post/" + post.getId();
			}
			return "redirect:/admin/add-post";
		}
		if (post.getUserID() == 0)
			post.setUserID(userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
					.getId());
		post.setCreateDate(new Date());
		postService.save(post);
		redirect.addAttribute("success", "Submit Successfully!");
		return "redirect:/admin/posts";
	}
}
