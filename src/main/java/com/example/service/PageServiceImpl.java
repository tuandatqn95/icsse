package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Page;
import com.example.repository.PageRepository;

@Service
public class PageServiceImpl implements PageService {

	@Autowired
	private PageRepository pageRepository;

	@Override
	public void save(Page page) {
		pageRepository.save(page);

	}

	@Override
	public void deleteById(int id) {
		pageRepository.deleteById(id);
	}

	@Override
	public Page findBySlug(String slug) {
		return pageRepository.findBySlug(slug);
	}

	@Override
	public Page findByID(int id) {
		return pageRepository.findById(id).get();
	}
}
