package com.blog.JavaSpringBoot.dto.response;

import lombok.Data;

/**
 * ResponseAuthorDTO
 */
@Data
public class ResponseBlogAuthorDTO {
    private Integer id;
    private String first_name;
    private String last_name;
    private String username;
}