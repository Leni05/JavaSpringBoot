package com.blog.JavaSpringBoot.service;

import com.blog.JavaSpringBoot.common.util.DateTime;
import com.blog.JavaSpringBoot.model.Author;
import com.blog.JavaSpringBoot.repository.AuthorRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import lombok.extern.slf4j.Slf4j;
/**
 * Authorservice
 */
@Slf4j
@Service
public class Authorservice {
    @Autowired
    AuthorRepository authorRepository;
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public Author save(Author author) {
        author.setPassword(passwordEncoder().encode(author.getPassword()));

        return authorRepository.save(author);
    }

    public Author changePassword(Integer id, Author author) {
        author.setId(id);
        author.setPassword(passwordEncoder().encode(author.getPassword()));

        return authorRepository.save(author);
    }

    public Author update(Integer id, Author author){
        author.setId((id));

        return authorRepository.save(author);
    }

    @Autowired
    private DateTime dateTime;

    private static final String RESOURCE = "Authors";
    private static final String FIELD = "id";


    public Page<Author> findAll(Pageable pageable) {
        try {

            return authorRepository.findAll(pageable).map(this::fromEntity);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw e;
        }
    }

    public Page<Author> findAuthor(Pageable pageable, String param) {

        try {
            param = param.toLowerCase();
            return authorRepository.findAuthor(pageable, param).map(this::fromEntity);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw e;
        }
    }

    private Author fromEntity(Author authors) {
        Author response = new Author();
        BeanUtils.copyProperties(authors, response);
        return response;
    }
    
}