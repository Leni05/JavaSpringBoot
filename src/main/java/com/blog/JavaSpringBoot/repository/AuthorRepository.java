package com.blog.JavaSpringBoot.repository;

import com.blog.JavaSpringBoot.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * AuthorRepository
 */
public interface AuthorRepository extends JpaRepository<Author, Integer>{

	Author findByUsername(String username);
    
}