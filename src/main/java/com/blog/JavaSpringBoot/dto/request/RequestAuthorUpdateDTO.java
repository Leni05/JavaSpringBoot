package com.blog.JavaSpringBoot.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
/**
 * RequestAuthorUpdateDTO
 */
@Data
public class RequestAuthorUpdateDTO {
    
    @Column(length = 45, nullable = false)
    @Size(min = 3, max = 45)
    private String first_name;

    @Column(length = 45)
    @Size(min = 3, max = 45)
    private String last_name;

    @Column(length = 45, nullable = false, unique = true)
    @Size(min = 3, max = 45)
    private String username;
    
    private Integer roles_id;

}