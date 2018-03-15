package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.model.Page;
import com.example.model.User;
import com.example.service.PageService;
import com.example.service.UserService;

@Component
public class DataSeedingListener implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private UserService userService;

	@Autowired
	private PageService pageService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {

		// Admin account
		if (userService.findByUsername("admin") == null) {
			User admin = new User();
			admin.setUsername("admin");
			admin.setPassword(passwordEncoder.encode("123456"));
			userService.save(admin);
		}

		User admin = userService.findByUsername("admin");
		// StaticPage
		if (pageService.findBySlug("home") == null)
			pageService.save(new Page(0, "title", "content", "home", admin.getId()));
		if (pageService.findBySlug("about-icsse") == null)
			pageService.save(new Page(0, "title", "content", "about-icsse", admin.getId()));
		if (pageService.findBySlug("about-hcmute") == null)
			pageService.save(new Page(0, "title", "content", "about-hcmute", admin.getId()));
		if (pageService.findBySlug("about-committees") == null)
			pageService.save(new Page(0, "title", "content", "about-committees", admin.getId()));
		if (pageService.findBySlug("for-author-call-for-papers") == null)
			pageService.save(new Page(0, "title", "content", "for-author-call-for-papers", admin.getId()));
		if (pageService.findBySlug("for-author-submission") == null)
			pageService.save(new Page(0, "title", "content", "for-author-submission", admin.getId()));
		if (pageService.findBySlug("for-author-registration") == null)
			pageService.save(new Page(0, "title", "content", "for-author-registration", admin.getId()));
		if (pageService.findBySlug("keynote-speakers") == null)
			pageService.save(new Page(0, "title", "content", "keynote-speakers", admin.getId()));
		if (pageService.findBySlug("publication") == null)
			pageService.save(new Page(0, "title", "content", "publication", admin.getId()));
		if (pageService.findBySlug("venue-hotel") == null)
			pageService.save(new Page(0, "title", "content", "venue-hotel", admin.getId()));
		if (pageService.findBySlug("gallery") == null)
			pageService.save(new Page(0, "title", "content", "gallery", admin.getId()));
		if (pageService.findBySlug("contact") == null)
			pageService.save(new Page(0, "title", "content", "contact", admin.getId()));
	}

}
