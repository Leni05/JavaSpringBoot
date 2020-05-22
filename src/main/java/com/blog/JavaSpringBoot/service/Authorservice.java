package com.blog.JavaSpringBoot.service;

import com.blog.JavaSpringBoot.model.Author;
import com.blog.JavaSpringBoot.repository.AuthorRepository;

import com.blog.JavaSpringBoot.exception.ResponseBase;
import com.blog.JavaSpringBoot.dto.response.ResponseAuthorDTO;
import com.blog.JavaSpringBoot.dto.response.ResponseAuthorPasswordDTO;
import com.blog.JavaSpringBoot.dto.response.ResponseAuthorUpdateDTO;

import java.util.HashMap;

import com.blog.JavaSpringBoot.dto.request.RequestAuthorDTO;
import com.blog.JavaSpringBoot.dto.request.RequestAuthorPasswordDTO;
import com.blog.JavaSpringBoot.dto.request.RequestAuthorUpdateDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.HttpRequestMethodNotSupportedException;
/**
 * Authorservice
 */
public interface Authorservice {
    

    Page<ResponseAuthorDTO> findAll(Pageable pageable);

    ResponseAuthorDTO findById(Integer id);

    Page<ResponseAuthorDTO> findByName(Pageable pageable, String param);

    ResponseEntity save(RequestAuthorDTO request);

    ResponseEntity update(Integer id, RequestAuthorUpdateDTO request);

    ResponseAuthorPasswordDTO updatePass(Integer id, RequestAuthorPasswordDTO request);

    ResponseEntity deleteById(Integer id);

    Author getByUsername(String username);

    OAuth2AccessToken getToken(HashMap<String, String> params) throws HttpRequestMethodNotSupportedException;
    OAuth2AccessToken getToken(Author author) throws HttpRequestMethodNotSupportedException;


    
}