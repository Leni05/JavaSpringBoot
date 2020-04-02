package com.blog.JavaSpringBoot.repository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.blog.JavaSpringBoot.exeption.ResponseBase;
import com.blog.JavaSpringBoot.model.Categories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * CategoriesRepository
 */
@Transactional(readOnly = true)
public interface CategoriesRepository extends JpaRepository<Categories, Integer> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM categories WHERE categories.id = ?1", nativeQuery = true)
    void deleteCataegorieById(Integer id);

}
