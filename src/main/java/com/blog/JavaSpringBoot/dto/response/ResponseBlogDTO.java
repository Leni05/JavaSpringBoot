package com.blog.JavaSpringBoot.dto.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.blog.JavaSpringBoot.model.Author;
import com.blog.JavaSpringBoot.model.Categories;
import com.blog.JavaSpringBoot.model.Tags;
import com.blog.JavaSpringBoot.model.Comment;

import lombok.Data;


/**
 * ResponseBlogDTO
 */
@Data
public class ResponseBlogDTO {

    private Integer id;    
    private String name;
    private Author author;
    private Categories categories;
    
    private List<Tags> tag = new ArrayList<>();

    private Set<Comment> comment;
}