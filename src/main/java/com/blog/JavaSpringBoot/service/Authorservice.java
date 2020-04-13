package com.blog.JavaSpringBoot.service;

import com.blog.JavaSpringBoot.model.Author;
import com.blog.JavaSpringBoot.repository.AuthorRepository;

import com.blog.JavaSpringBoot.exception.ResponseBase;
import com.blog.JavaSpringBoot.dto.response.ResponseAuthorDTO;
import com.blog.JavaSpringBoot.dto.response.ResponseAuthorPasswordDTO;
import com.blog.JavaSpringBoot.dto.response.ResponseAuthorUpdateDTO;
import com.blog.JavaSpringBoot.dto.request.RequestAuthorDTO;
import com.blog.JavaSpringBoot.dto.request.RequestAuthorPasswordDTO;
import com.blog.JavaSpringBoot.dto.request.RequestAuthorUpdateDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
/**
 * Authorservice
 */
public interface Authorservice {
    

    Page<ResponseAuthorDTO> findAll(Pageable pageable);

    ResponseAuthorDTO findById(Integer id);

    Page<ResponseAuthorDTO> findByName(Pageable pageable, String param);

    ResponseAuthorDTO save(RequestAuthorDTO request);

    ResponseAuthorUpdateDTO update(Integer id, RequestAuthorUpdateDTO request);

    ResponseAuthorPasswordDTO updatePass(Integer id, RequestAuthorPasswordDTO request);

    ResponseAuthorDTO deleteById(Integer id);

    // @Autowired
    // AuthorRepository authorRepository;
    
    // @Bean
    // public BCryptPasswordEncoder passwordEncoder() {
    //     return new BCryptPasswordEncoder();
    // }

    // public Author save(Author author) {
    //     author.setPassword(passwordEncoder().encode(author.getPassword()));

    //     return authorRepository.save(author);
    // }

    // public Author changePassword(Integer id, Author author) {
    //     author.setId(id);
    //     author.setPassword(passwordEncoder().encode(author.getPassword()));

    //     return authorRepository.save(author);
    // }

    // public Author update(Integer id, Author author){
    //     author.setId((id));

    //     return authorRepository.save(author);
    // }

    // @Autowired
    // private DateTime dateTime;

    // private static final String RESOURCE = "Authors";
    // private static final String FIELD = "id";


    // public Page<Author> findAll(Pageable pageable) {
    //     try {

    //         return authorRepository.findAll(pageable).map(this::fromEntity);

    //     } catch (Exception e) {

    //         log.error(e.getMessage(), e);
    //         throw e;
    //     }
    // }

    // public Page<Author> findAuthor(Pageable pageable, String param) {

    //     try {
    //         param = param.toLowerCase();
    //         return authorRepository.findAuthor(pageable, param).map(this::fromEntity);

    //     } catch (Exception e) {

    //         log.error(e.getMessage(), e);
    //         throw e;
    //     }
    // }

    // private Author fromEntity(Author authors) {
    //     Author response = new Author();
    //     BeanUtils.copyProperties(authors, response);
    //     return response;
    // }
    
}