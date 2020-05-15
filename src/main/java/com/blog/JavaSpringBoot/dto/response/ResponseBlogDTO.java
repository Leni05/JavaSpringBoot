package com.blog.JavaSpringBoot.dto.response;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.blog.JavaSpringBoot.model.Author;
import com.blog.JavaSpringBoot.model.Categories;
import com.blog.JavaSpringBoot.model.Tags;
import com.blog.JavaSpringBoot.model.Comment;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;


/**
 * ResponseBlogDTO
 */
@Data
public class ResponseBlogDTO {

    private Integer id;    
    private String title; 
    private String content;
    private Author author;
    private Categories categories;
    
    private List<Tags> tag = new ArrayList<>();

    private Set<Comment> comment;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm:ss",timezone="GMT+7")
    private Date created_at;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm:ss",timezone="GMT+7")
    private Date updated_at; 
}