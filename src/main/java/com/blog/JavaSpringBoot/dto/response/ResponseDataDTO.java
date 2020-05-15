package com.blog.JavaSpringBoot.dto.response;

import org.springframework.http.ResponseEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResponseDataDTO<Any> {

    private Boolean status = true;
    private Integer code = 200;
    private String message = "Success";
    private Any data;

    public ResponseDataDTO(Boolean status, Integer code, String message, Any data) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResponseDataDTO() {
    }

}