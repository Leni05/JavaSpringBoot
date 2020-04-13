package com.blog.JavaSpringBoot.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
/**
 * RequestAuthorPasswordDTO
 */
@Data
public class RequestAuthorPasswordDTO {
    
    @Column(length = 150, nullable = false)
    private String password;

}