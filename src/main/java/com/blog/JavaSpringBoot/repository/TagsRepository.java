package com.blog.JavaSpringBoot.repository;

import com.blog.JavaSpringBoot.model.Tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import antlr.collections.List;

/**
 * TagsRepository
 */
@Transactional(readOnly = true)
public interface TagsRepository extends PagingAndSortingRepository<Tags, Integer> {
    Tags findByName(String name);
   
   	@Query("select e from #{#entityName} e where e.name like %:param% ")
	Page<Tags> findByNameParams(Pageable pageable, String param);
    
}