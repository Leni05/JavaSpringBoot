package com.blog.JavaSpringBoot.repository;


import com.blog.JavaSpringBoot.model.Author;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import antlr.collections.List;
/**
 * AuthorRepository
 */
@Transactional(readOnly = true)
public interface AuthorRepository extends PagingAndSortingRepository<Author, Integer>{

	Author findByUsername(String username);
   
	@Query(
        "select e from #{#entityName} e where e.first_name like %:param% OR "
        + "e.last_name like %:param% OR e.username like %:param%"
       )
        Page<Author> findAuthor(Pageable pageable, String param);
        
        @Query("select e from #{#entityName} e where e.username like %:username% ")
        Author getUserByUsername(String username);
    
}