package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Page;

@Repository
public interface PageRepository extends JpaRepository<Page, Integer> {

	Page findBySlug(String slug);
}
