package com.blog.JavaSpringBoot.service.impl;

import com.blog.JavaSpringBoot.exception.InternalServerErrorException;
import com.blog.JavaSpringBoot.service.LoggingService;
import com.blog.JavaSpringBoot.log.dto.request.SystemLogRequestDto;
import com.blog.JavaSpringBoot.log.service.SlmService;
import com.blog.JavaSpringBoot.model.Author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class LoggingServiceImpl implements LoggingService {

    @Autowired
    private SlmService slmService;

    /** 
     * String type [Create, Update, Delete, Process, Login, Import]
     * String method name of method
     * String code status code
     * String message message status
     * String description stack trace or system message
     * String time execution time method
     **/
    @Override
    public Boolean createLooging(String type, String method, String code, String message, String description, String time) {
        SystemLogRequestDto dataLog = new SystemLogRequestDto();
        System.out.println("masukkkkkkkk service impl");
        try {
            // Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            // String username = auth.getPrincipal().toString();
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Author user = (Author) auth.getPrincipal();     
            
            String username = user.getUsername();
            
            dataLog.setModule("BLOG");
            dataLog.setMethod(method);
            dataLog.setType(type);
            dataLog.setCode(code);
            dataLog.setMessage(message);
            dataLog.setDescription(description);
            dataLog.setTime(time);
            dataLog.setUser(null);
            dataLog.setUsername(username);

            slmService.createLog(dataLog);

            return true;
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }
    
}