package com.blog.JavaSpringBoot.service;

import com.blog.JavaSpringBoot.model.Comment;
import com.blog.JavaSpringBoot.repository.CommentRepository;

import com.blog.JavaSpringBoot.exception.ResponseBase;
import com.blog.JavaSpringBoot.dto.response.ResponseBaseDTO;
import com.blog.JavaSpringBoot.dto.response.ResponseCommentDTO;
import com.blog.JavaSpringBoot.dto.request.RequestCommentDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * CommentService
 */
public interface CommentService {

    Page<ResponseCommentDTO> findAll(Pageable pageable, Integer blog);

    ResponseCommentDTO findByIdAndBlogId(Integer id, Integer blog);

    Page<ResponseCommentDTO> findByEmail(Pageable pageable,Integer blog, String param);

    ResponseCommentDTO save(Integer blog , RequestCommentDTO request);

    ResponseBaseDTO deleteById(Integer id, Integer blog);

    // @Autowired
    // private CommentRepository commentRepository;

    // public Comment update(Integer id, Comment comment) {
    //     comment.setId(id);

    //     return commentRepository.save(comment);
    // }

    // public Page<Comment> findAll(Pageable pageable, Integer blog) {

    //     try {
    //         return commentRepository.findCommentByBlogId(pageable, blog).map(this::fromEntity);


    //     } catch (Exception e) {

    //         log.error(e.getMessage(), e);
    //         throw e;
    //     }
    // }


    // public Page<Comment> findByEmail(Pageable pageable,Integer blog, String param) {

    //     try {
    //         param = param.toLowerCase();
    //         return commentRepository.findByEmail(pageable, blog, param).map(this::fromEntity);

    //     } catch (Exception e) {

    //         log.error(e.getMessage(), e);
    //         throw e;
    //     }
    // }

    // private Comment fromEntity(Comment comments) {
    //     Comment response = new Comment();
    //     BeanUtils.copyProperties(comments, response);
    //     return response;
    // }
}