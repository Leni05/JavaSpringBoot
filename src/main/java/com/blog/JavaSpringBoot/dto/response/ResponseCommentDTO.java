package com.blog.JavaSpringBoot.dto.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.blog.JavaSpringBoot.model.Blog;

import lombok.Data;

/**
 * ResponseCommentDTO
 */
@Data
public class ResponseCommentDTO {
    private Integer id;      
    private String guest_email;
    private String content;   
    private Integer blog_id;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm:ss",timezone="GMT+7")
    private Date created_at;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm:ss",timezone="GMT+7")
    private Date updated_at; 
}