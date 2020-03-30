package com.blog.JavaSpringBoot.repository;

import com.blog.JavaSpringBoot.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * BlogRepository
 */
public interface BlogRepository extends JpaRepository<Blog, Integer> {

    
}