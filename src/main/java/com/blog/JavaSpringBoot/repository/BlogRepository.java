package com.blog.JavaSpringBoot.repository;

import com.blog.JavaSpringBoot.model.Author;
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
    
    @Query("select e from #{#entityName} e where e.title like %:param% ")
    Page<Blog> search(Pageable pageable, String param);    


    @Query(value = "SELECT * from blog WHERE id = ?1", nativeQuery = true)
    Page<Blog> findByIdCategory(Pageable pageable, Integer categoryId);

    @Query(value = "SELECT * from blog WHERE author_id = ?1", nativeQuery = true)
    Page<Blog> findByIdAuthor(Pageable pageable, Integer authorId);

    @Query(value = "select blog.* from blog join blog_tags on blog.id = blog_tags.blog_id "
    +"join tags on tags.id = blog_tags.tags_id "
    +"where tags.name like %:param%  ", nativeQuery = true)
    Page<Blog> findByTag(Pageable pageable, String param);
    
    @Query(value = "SELECT * from blog join author on blog.author_id= author.id "
    +"where blog.id = :id", nativeQuery = true)
    Blog getById(Integer id);

    
}