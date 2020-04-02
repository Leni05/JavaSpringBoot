package com.blog.JavaSpringBoot.model.request;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

/**
 * BlogDto
 */
@Setter
@Getter
public class BlogDto {

    
    @Column(length = 150, nullable = false)
    @Size(min = 3, max = 150)
    @NotBlank
    private String title;

    @Column(length = 1000, nullable = false, columnDefinition = "TEXT")
    @Size(min = 10, max = 1000)
    @NotBlank
    
    private String content;
    @Column(length = 20)
    @Size(min = 2, max = 20)
    @NotBlank
    private String tags_name;
}