package com.blog.JavaSpringBoot.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javassist.NotFoundException;
import com.blog.JavaSpringBoot.exeption.ResponseBase;
import com.blog.JavaSpringBoot.model.Author;
import com.blog.JavaSpringBoot.repository.AuthorRepository;
import com.blog.JavaSpringBoot.service.Authorservice;
import com.blog.JavaSpringBoot.model.Categories;
import com.blog.JavaSpringBoot.model.Tags;
import com.blog.JavaSpringBoot.repository.CategoriesRepository;
import com.blog.JavaSpringBoot.service.CategoriesService;
import com.blog.JavaSpringBoot.model.Blog;
import com.blog.JavaSpringBoot.repository.BlogRepository;
import com.blog.JavaSpringBoot.service.BlogService;
import com.blog.JavaSpringBoot.model.Tags;
import com.blog.JavaSpringBoot.repository.TagsRepository;
import com.blog.JavaSpringBoot.model.request.BlogDto;

/**
 * BlogController
 */

@RestController
@RequestMapping("/blogs")
public class BlogController {
    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private BlogService blogService;

    @Autowired
    private TagsRepository tagsRepository;

    @GetMapping()
    public ResponseEntity<ResponseBase> getBlog() {
        ResponseBase response = new ResponseBase<>();

        response.setData(blogRepository.findAll());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ResponseBase> getBlogById(@PathVariable Integer id) throws NotFoundException {
        ResponseBase response = new ResponseBase<>();

        Blog blog = blogRepository.findById(id).orElseThrow(() -> new NotFoundException("Tags id " + id + " NotFound"));

        response.setData(blog);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ResponseBase> postBlog(@RequestBody Blog blog) throws NotFoundException {
        ResponseBase response = new ResponseBase<>();

        Author author = authorRepository.findById(blog.getAuthor_id()).orElseThrow(() -> new NotFoundException("Blog id " + blog.getAuthor_id() + " NotFound"));
        Categories categories = categoriesRepository.findById(blog.getCategories_id()).orElseThrow(() -> new NotFoundException("Category id " + blog.getCategories_id() + " NotFound"));
       
      
        List<String> tagtag = blog.getTags_name();
        ArrayList<Tags> tags = new ArrayList<Tags>();

        for (String tag : tagtag) {
            Tags val = tagsRepository.findByName(tag);
            
            if(val == null) { 
                Tags tagsData = new Tags();
                tagsData.setName(tag);
                tagsRepository.save(tagsData);
        
                Tags valTags = tagsRepository.findById(tagsData.getId()).orElseThrow(() -> new NotFoundException("Tags id " + tag + " NotFound"));
                tags.add(valTags);
                // tags.add(val);
            } else {
                // Tags valTasg = tagsRepository.findById(val.getId()).orElseThrow(() -> new NotFoundException("Tags id " + tag + " NotFound"));
                tags.add(val);
            }
            
        }

        blog.setAuthor(author);
        blog.setCategories(categories);
        blog.setTag(tags);

        try {
            response.setData(blogRepository.save(blog));
        } catch (Exception e) {
            response.setStatus(false);
            response.setCode(500);
            response.setMessage(e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    
}