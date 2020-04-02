package com.blog.JavaSpringBoot.repository;

import com.blog.JavaSpringBoot.model.Tags;
import org.springframework.data.jpa.repository.JpaRepository;

import antlr.collections.List;

/**
 * TagsRepository
 */
public interface TagsRepository extends JpaRepository<Tags, Integer> {
    Tags findByName(String name);
    
}