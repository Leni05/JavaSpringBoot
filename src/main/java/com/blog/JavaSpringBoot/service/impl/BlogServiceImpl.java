package com.blog.JavaSpringBoot.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.blog.JavaSpringBoot.dto.request.RequestBlogDTO;
import com.blog.JavaSpringBoot.dto.request.RequestBlogUpdateDTO;
import com.blog.JavaSpringBoot.dto.response.ResponseBlogDTO;
import com.blog.JavaSpringBoot.exception.ResourceNotFoundException;
import com.blog.JavaSpringBoot.repository.AuthorRepository;
import com.blog.JavaSpringBoot.repository.BlogRepository;
import com.blog.JavaSpringBoot.repository.CategoriesRepository;
import com.blog.JavaSpringBoot.repository.TagsRepository;
import com.blog.JavaSpringBoot.model.Author;
import com.blog.JavaSpringBoot.model.Blog;
import com.blog.JavaSpringBoot.model.Categories;
import com.blog.JavaSpringBoot.model.Tags;
import com.blog.JavaSpringBoot.service.BlogService;
import com.blog.JavaSpringBoot.util.DateTime;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * TagServiceImpl
 */
@Slf4j
@Service
public class BlogServiceImpl implements BlogService {
    
    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private TagsRepository tagsRepository;

    @Autowired
    private DateTime dateTime;

    private static final String RESOURCE = "Tags";
    private static final String FIELD = "id";

    // @Override
    // public ResponseTagsDTO deleteById(Integer id) {
    //     try {
    //         Tags tags = tagsRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(id.toString(), FIELD, RESOURCE));
    //         tagsRepository.deleteById(id);

    //         return fromEntity(tags);

    //     } catch (Exception e) {
    //         log.error(e.getMessage(), e);
    //         throw e;
    //     }
    // }

    @Override
    public Page<ResponseBlogDTO> findAll(Pageable pageable) {
        try {
            return blogRepository.findAll(pageable).map(this::fromEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

  
    @Override
    public ResponseBlogDTO findById(Integer id) {
        try {
            Blog blogs = blogRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(id.toString(), FIELD, RESOURCE));
            
            return fromEntity(blogs);
        } catch (ResourceNotFoundException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Page<ResponseBlogDTO> findByName(Pageable pageable, String param) {
        try {
            param = param.toLowerCase();
            return blogRepository.search(pageable, param).map(this::fromEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public ResponseBlogDTO save (RequestBlogDTO request) {

        Author author = authorRepository.findById(request.getAuthor_id()).orElseThrow(
            ()->new ResourceNotFoundException(request.getAuthor_id().toString(), FIELD, RESOURCE)
        );   
        Categories categories = categoriesRepository.findById(request.getCategories_id()).orElseThrow(
            ()->new ResourceNotFoundException(request.getCategories_id().toString(), FIELD, RESOURCE)
        );   
      
        List<String> tagtag = request.getTags_name();
        ArrayList<Tags> tags = new ArrayList<Tags>();

        for (String tag : tagtag) {
            Tags val = tagsRepository.findByName(tag);
            
            if(val == null) { 
                Tags tagsData = new Tags();
                tagsData.setName(tag);
                tagsRepository.save(tagsData);
        
                Tags valTags = tagsRepository.findById(tagsData.getId()).orElseThrow(
                    ()->new ResourceNotFoundException(tagsData.getId().toString(), FIELD, RESOURCE)
                );   
                tags.add(valTags);
              
            } else {
               tags.add(val);
            }
            
        }

        try {         
            Blog blogs = new Blog();

            blogs.setAuthor((author));
            blogs.setCategories(categories);
            blogs.setTag(tags);
            blogs.setCreated_at(new Date());
            blogs.setUpdated_at(new Date());

            blogRepository.save(blogs);
            return fromEntity(blogs);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }        
      
    }
    
    @Override
    public ResponseBlogDTO update(Integer id, RequestBlogUpdateDTO request) {
        try {
            Blog blogs = blogRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException(id.toString(), FIELD, RESOURCE)
            );   
            
            BeanUtils.copyProperties(request, blogs);
            blogs.setUpdated_at(new Date());
            blogRepository.save(blogs);

            return fromEntity(blogs);
        } catch (ResourceNotFoundException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    private ResponseBlogDTO fromEntity(Blog blogs) {
        ResponseBlogDTO response = new ResponseBlogDTO();
        BeanUtils.copyProperties(blogs, response);
        return response;
    }
}