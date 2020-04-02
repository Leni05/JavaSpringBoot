package com.blog.JavaSpringBoot.service;

import com.blog.JavaSpringBoot.model.Comment;
import com.blog.JavaSpringBoot.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * CommentService
 */
@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public Comment update(Integer id, Comment comment) {
        comment.setId(id);

        return commentRepository.save(comment);
    }
}