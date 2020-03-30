package com.blog.JavaSpringBoot.repository;

import com.blog.JavaSpringBoot.model.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * CategoriesRepository
 */
public interface CategoriesRepository extends JpaRepository<Categories, Integer> {

    
}
