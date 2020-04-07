package com.blog.JavaSpringBoot.service;


import com.blog.JavaSpringBoot.common.util.DateTime;
import com.blog.JavaSpringBoot.model.Author;
import com.blog.JavaSpringBoot.model.Blog;
import com.blog.JavaSpringBoot.model.Categories;
import com.blog.JavaSpringBoot.repository.AuthorRepository;
import com.blog.JavaSpringBoot.repository.BlogRepository;
import com.blog.JavaSpringBoot.repository.CategoriesRepository;
import com.blog.JavaSpringBoot.service.BlogService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * CategoriesServiceImpl
 */
@Slf4j
@Service
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private AuthorRepository authorRepository;
    
    @Autowired
    private CategoriesRepository categoriesRepository;
    
    @Autowired
    private DateTime dateTime;

    private static final String RESOURCE = "Blog";
    private static final String FIELD = "id";

    public Blog update(Integer id, Blog blog) {
        blog.setId(id);

        return blogRepository.save(blog);
    }


    public Page<Blog> findAll(Pageable pageable) {
        try {

            return blogRepository.findAll(pageable).map(this::fromEntity);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw e;
        }
    }

    public Page<Blog> findByNameParams(Pageable pageable, String param) {

        try {
            param = param.toLowerCase();
            return blogRepository.search(pageable, param).map(this::fromEntity);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw e;
        }
    }

    private Blog fromEntity(Blog blog) {
        Blog response = new Blog();
        BeanUtils.copyProperties(blog, response);
        return response;
    }
}