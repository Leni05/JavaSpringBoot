package com.blog.JavaSpringBoot.service;

import com.blog.JavaSpringBoot.model.Blog;
import com.blog.JavaSpringBoot.repository.BlogRepository;

import com.blog.JavaSpringBoot.exception.ResponseBase;
import com.blog.JavaSpringBoot.dto.response.ResponseBaseDTO;
import com.blog.JavaSpringBoot.dto.response.ResponseBlogDTO;

import javax.validation.Valid;

import com.blog.JavaSpringBoot.dto.request.RequestBlogDTO;
import com.blog.JavaSpringBoot.dto.request.RequestBlogUpdateDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * BlogService
 */
public interface BlogService {


    Page<ResponseBlogDTO> findAll(Pageable pageable);

    ResponseBlogDTO findById(Integer id);

    Page<ResponseBlogDTO> findByName(Pageable pageable, String param);

    ResponseBlogDTO save(RequestBlogDTO request);
    
    ResponseBaseDTO updateBlog(Integer id, RequestBlogUpdateDTO request);

    void deleteById(Integer id);

    Page<ResponseBlogDTO> findByIdCategory(Pageable pageable, Integer categoryId);

    Page<ResponseBlogDTO> findByIdAuthor(Pageable pageable, Integer authorId);

    Page<ResponseBlogDTO> findByTag(Pageable pageable, String tagName);

    // @Autowired
    // private BlogRepository blogRepository;

    // @Autowired
    // private AuthorRepository authorRepository;
    
    // @Autowired
    // private CategoriesRepository categoriesRepository;
    
    // @Autowired
    // private DateTime dateTime;

    // private static final String RESOURCE = "Blog";
    // private static final String FIELD = "id";

    // public Blog update(Integer id, Blog blog) {
    //     blog.setId(id);

    //     return blogRepository.save(blog);
    // }


    // public Page<Blog> findAll(Pageable pageable) {
    //     try {

    //         return blogRepository.findAll(pageable).map(this::fromEntity);

    //     } catch (Exception e) {

    //         log.error(e.getMessage(), e);
    //         throw e;
    //     }
    // }

    // public Page<Blog> findByNameParams(Pageable pageable, String param) {

    //     try {
    //         param = param.toLowerCase();
    //         return blogRepository.search(pageable, param).map(this::fromEntity);

    //     } catch (Exception e) {

    //         log.error(e.getMessage(), e);
    //         throw e;
    //     }
    // }

    // private Blog fromEntity(Blog blog) {
    //     Blog response = new Blog();
    //     BeanUtils.copyProperties(blog, response);
    //     return response;
    // }
}