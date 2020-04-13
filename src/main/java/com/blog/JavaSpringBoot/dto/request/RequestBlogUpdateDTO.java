package com.blog.JavaSpringBoot.dto.request;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.blog.JavaSpringBoot.model.Author;
import com.blog.JavaSpringBoot.model.Categories;
import com.blog.JavaSpringBoot.model.Tags;

import lombok.Data;

/**
 * RequestBlogUpdateDTO
 */
@Data
public class RequestBlogUpdateDTO {

    @NotNull
    @NotBlank
    private String title;

    @NotNull
    @NotBlank
    private String content;


}