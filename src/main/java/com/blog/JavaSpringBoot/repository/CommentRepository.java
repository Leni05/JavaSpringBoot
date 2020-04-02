package com.blog.JavaSpringBoot.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.blog.JavaSpringBoot.exeption.ResponseBase;
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

    // @Query(value = "SELECT * from Comment WHERE blogs_id = ?1", nativeQuery = true)
    // List<Comment> findCommentBlog(Integer blog_id);

    // @Modifying
    // @Transactional
    // @Query(value = "DELETE FROM Comment WHERE comment.id = ?1 AND comment.blogs_id = ?2", nativeQuery = true)
    // void deleteCommentId(Integer id, Integer blogId);

    // Comment findByIdAndBlogId(Integer id, Integer blogId);
    
}