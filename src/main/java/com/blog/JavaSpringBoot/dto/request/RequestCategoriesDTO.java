package com.blog.JavaSpringBoot.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
/**
 * RequestCategoriesDTO
 */
@Data
public class RequestCategoriesDTO {

    @NotNull
    @NotBlank
    private String name;


}