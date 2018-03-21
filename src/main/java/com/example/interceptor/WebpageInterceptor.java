package com.example.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.example.service.PostService;

public class WebpageInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private PostService postService;

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("PostHandler");
		System.out.println(modelAndView);
		System.out.println(postService);
		//modelAndView.addObject("lastestposts", postService.getTop5Lastest());

	}
}
