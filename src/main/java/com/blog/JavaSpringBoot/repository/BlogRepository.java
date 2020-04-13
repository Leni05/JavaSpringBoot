package com.blog.JavaSpringBoot.repository;

import com.blog.JavaSpringBoot.model.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * BlogRepository
 */

@Transactional(readOnly = true)
public interface BlogRepository extends PagingAndSortingRepository<Blog, Integer> {
    
    @Query(
     "select e from #{#entityName} e where e.title like %:param% OR "
     + "e.content like %:param%"
    )
    Page<Blog> search(Pageable pageable, String param);    
}