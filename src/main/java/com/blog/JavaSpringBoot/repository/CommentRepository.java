package com.blog.JavaSpringBoot.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.blog.JavaSpringBoot.model.Comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * CommentRepository
 */
@Transactional(readOnly = true)
public interface CommentRepository extends JpaRepository<Comment, Integer>{

    @Query(value = "SELECT * from comment WHERE blog_id = ?1", nativeQuery = true)
    List<Comment> findCommentByBlogIdone(Integer blog);

    @Query(value = "SELECT * from comment WHERE blog_id = ?1", nativeQuery = true)
    Page<Comment> findCommentByBlogId(Pageable pageable,Integer blog);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM comment WHERE comment.id = ?1 AND comment.blog_id = ?2", nativeQuery = true)
    void deleteByIdAndBlogId(Integer id, Integer blog);

    @Query(value = "SELECT * from comment WHERE  comment.id = ?1 AND comment.blog_id = ?2", nativeQuery = true)
    Comment findByIdAndBlogId(Integer id, Integer blog);

    @Query(value = "SELECT * from comment WHERE blog_id = ?1 AND comment.guest_email like %?2% " ,nativeQuery = true )
    Page<Comment> findByEmail(Pageable pageable, Integer blog, String param);
    
    // @Query(value = "SELECT * from comment WHERE WHERE comment.id = ?1 and comment.blog_id = ?2", nativeQuery = true)
	// Comment findByIdComment(Integer id);
}