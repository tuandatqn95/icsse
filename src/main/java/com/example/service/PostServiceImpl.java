package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Post;
import com.example.repository.PostRepository;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;

	@Override
	public Post findById(int id) {
		return postRepository.getOne(id);
	}

	@Override
	public void save(Post post) {
		postRepository.save(post);
	}

	@Override
	public void deleteById(int id) {
		postRepository.deleteById(id);
	}

	@Override
	public List<Post> getTop5Lastest() {
		return postRepository.findAll();
	}

	@Override
	public List<Post> getListPost() {
		return postRepository.findAll();
	}

	@Override
	public boolean idExist(int id) {
		return postRepository.existsById(id);
	}

}
