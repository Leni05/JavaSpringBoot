package com.blog.JavaSpringBoot.repository;


import com.blog.JavaSpringBoot.model.Categories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import antlr.collections.List;
/**

/**
 * CategoriesRepository
 */

@Transactional(readOnly = true)
public interface CategoriesRepository extends JpaRepository<Categories, Integer> {

   
	@Query(
        "select e from #{#entityName} e where e.name like %:param%" )
	Page<Categories> findCategories(Pageable pageable, String param);
    

}
