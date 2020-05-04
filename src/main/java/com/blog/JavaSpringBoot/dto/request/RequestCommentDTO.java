package com.blog.JavaSpringBoot.dto.request;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.blog.JavaSpringBoot.model.Blog;

import lombok.Data;

/**
 * RequestCommentDTO
 */
@Data
public class RequestCommentDTO {

    // private Integer blogs_id;

    @NotNull
    @NotBlank
    private String guest_email;

    private String content;

    private Blog blog;



}