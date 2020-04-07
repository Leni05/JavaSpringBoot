package com.blog.JavaSpringBoot.service;

import com.blog.JavaSpringBoot.model.Comment;
import com.blog.JavaSpringBoot.repository.CommentRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import antlr.collections.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * CommentService
 */
@Slf4j
@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public Comment update(Integer id, Comment comment) {
        comment.setId(id);

        return commentRepository.save(comment);
    }

    public Page<Comment> findAll(Pageable pageable, Integer blog) {

        try {
            return commentRepository.findCommentByBlogId(pageable, blog).map(this::fromEntity);


        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw e;
        }
    }


    public Page<Comment> findByEmail(Pageable pageable, String param) {

        try {
            param = param.toLowerCase();
            return commentRepository.findByEmail(pageable, param).map(this::fromEntity);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw e;
        }
    }

    private Comment fromEntity(Comment comments) {
        Comment response = new Comment();
        BeanUtils.copyProperties(comments, response);
        return response;
    }
}