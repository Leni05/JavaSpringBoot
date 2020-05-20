package com.blog.JavaSpringBoot.dto.response;


import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * ResponseAuthorDTO
 */
@Data
public class ResponseAuthorDTO {
    private Integer id;
    private ResponseRolesDTO role;
    private String first_name;
    private String last_name;
    private String username;
    private String password;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm:ss",timezone="GMT+7")
    private Date created_at;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm:ss",timezone="GMT+7")
    private Date updated_at; 
}