package com.blog.JavaSpringBoot.service;

import com.blog.JavaSpringBoot.model.Categories;
import com.blog.JavaSpringBoot.repository.CategoriesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * CategoriesService
 */
@Service
public class CategoriesService {
    @Autowired
    CategoriesRepository categoriesRepository;

    public Categories update(Integer id, Categories categories) {
        categories.setId(id);

        return categoriesRepository.save(categories);
    }

    
}