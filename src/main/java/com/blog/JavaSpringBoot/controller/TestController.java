package com.blog.JavaSpringBoot.controller;

import com.blog.JavaSpringBoot.dto.response.ResponseBaseDTO;
import com.blog.JavaSpringBoot.exception.InternalServerErrorException;
import com.blog.JavaSpringBoot.model.Author;
import com.blog.JavaSpringBoot.service.LoggingService;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.blog.JavaSpringBoot.log.service.SlmService;

@RestController
@RequestMapping(path = "/test")
public class TestController {

    // @Autowired
    // PrincipalExtractor extractor;

    @Autowired
    private LoggingService loggingService;

    @Autowired
    private SlmService slmService;

    @GetMapping("check")
    public ResponseEntity testApi() {
        return new ResponseEntity(new ResponseBaseDTO<>(true, "200", "Success", null), HttpStatus.OK);
    }

    @GetMapping("remote")
    public Object getRemote() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Author userSession = (Author) auth.getPrincipal();

        return userSession;
    }

    @GetMapping("check-session")
    public ResponseEntity checkSession() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getPrincipal().toString();

            return new ResponseEntity(new ResponseBaseDTO<>(true, "200", "Success", username), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new ResponseBaseDTO<>(false, "500", e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/logs")
    public String getTestLog() {
        try {
            System.out.println("masukkkkkkkkk");
            loggingService.createLooging("test", "getTest", "200", "Test create", "Test from Controller", "0");
            // slmService.getMessage();
            return "Log Create";
        } catch (Exception e) {
            System.out.println("gagaaalllll");
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @GetMapping("message")
    public ResponseEntity getMessage() {
        return new ResponseEntity(new ResponseBaseDTO<>(true, "200", "Success", null), HttpStatus.OK);
    }


}
