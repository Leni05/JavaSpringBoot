package com.blog.JavaSpringBoot.dto.response;


import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * ResponseRolesDTO
 */
@Data
public class ResponseRolesDTO {


    private Integer id;
    private String name;
    private String description;
    
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm:ss",timezone="GMT+7")
    private Date created_at;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm:ss",timezone="GMT+7")  
    private Date updated_at;
    
}