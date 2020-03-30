package com.blog.JavaSpringBoot.service;

import com.blog.JavaSpringBoot.model.Author;
import com.blog.JavaSpringBoot.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Authorservice
 */
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
    
}