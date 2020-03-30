package com.blog.JavaSpringBoot.service;

import com.blog.JavaSpringBoot.model.Blog;
import com.blog.JavaSpringBoot.repository.BlogRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * BlogService
 */
@Service
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;

    public Blog update(Integer id, Blog blog) {
        blog.setId(id);

        return blogRepository.save(blog);
    }
}