package com.blog.JavaSpringBoot.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
/**
 * RequestcreateTagsDTO
 */
@Data
public class RequestTagsDTO {

    @NotNull
    @NotBlank
    private String name;


}