package com.example.service;

import java.util.List;

import com.example.model.Post;

public interface PostService {

	Post findById(int id);

	void save(Post post);

	void deleteById(int id);

	List<Post> getTop5Lastest();

	List<Post> getListPost();
	
	boolean idExist(int id);
}
