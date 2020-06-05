package com.blog.JavaSpringBoot.log.service;

import com.blog.JavaSpringBoot.log.dto.request.SystemLogRequestDto;

public interface SlmService {
    
    public String getMessage();
    public Boolean createLog(SystemLogRequestDto dataLog);

}