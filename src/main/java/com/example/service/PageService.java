package com.example.service;

import com.example.model.Page;

public interface PageService {

	void save(Page page);

	void deleteById(int id);

	Page findBySlug(String slug);

	Page findByID(int id);

}
