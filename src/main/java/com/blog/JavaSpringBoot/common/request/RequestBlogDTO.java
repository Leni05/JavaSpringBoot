package com.blog.JavaSpringBoot.common.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 * RequestAuthorDTO
 */
@Data
public class RequestBlogDTO {
    @NotNull
    private Integer authorId;

    @NotNull
    private Integer categoriesId;

    @NotNull
    @NotBlank
    private String title;

    @NotNull
    @NotBlank
    private String content;
}