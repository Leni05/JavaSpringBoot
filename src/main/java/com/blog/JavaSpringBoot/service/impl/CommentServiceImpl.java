package com.blog.JavaSpringBoot.service.impl;

import java.util.Date;

import javax.persistence.EntityNotFoundException;

import com.blog.JavaSpringBoot.dto.request.RequestCommentDTO;
import com.blog.JavaSpringBoot.dto.response.ResponseBaseDTO;
import com.blog.JavaSpringBoot.dto.response.ResponseCommentDTO;
import com.blog.JavaSpringBoot.exception.ResourceNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.blog.JavaSpringBoot.repository.CommentRepository;
import com.blog.JavaSpringBoot.repository.BlogRepository;
import com.blog.JavaSpringBoot.model.Blog;
import com.blog.JavaSpringBoot.model.Comment;
import com.blog.JavaSpringBoot.service.CommentService;
import com.blog.JavaSpringBoot.util.DateTime;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * TagServiceImpl
 */
@Slf4j
@Service
public class CommentServiceImpl implements CommentService {
    
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    BlogRepository blogRepository;

    @Autowired
    private DateTime dateTime;

    private static final String RESOURCE = "Tags";
    private static final String FIELD = "id";

    // @Override
    // public ResponseTagsDTO deleteById(Integer id) {
    //     try {
    //         Tags tags = tagsRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(id.toString(), FIELD, RESOURCE));
    //         tagsRepository.deleteById(id);

    //         return fromEntity(tags);

    //     } catch (Exception e) {
    //         log.error(e.getMessage(), e);
    //         throw e;
    //     }
    // }

    @Override
    public Page<ResponseCommentDTO> findAll(Pageable pageable, Integer blog) {
        try {
            return commentRepository.findCommentByBlogId(pageable, blog).map(this::fromEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public ResponseCommentDTO findByIdAndBlogId(Integer id, Integer blog) {
        try {
            Comment comments = commentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(id.toString(), FIELD, RESOURCE));
            
            return fromEntity(comments);
        } catch (ResourceNotFoundException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Page<ResponseCommentDTO> findByEmail(Pageable pageable, Integer blog, String param) {
        try {
            param = param.toLowerCase();
            return commentRepository.findByEmail(pageable, blog, param).map(this::fromEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public ResponseCommentDTO save(Integer blog, RequestCommentDTO request) {

        Blog blogData = blogRepository.findById(blog).orElseThrow(()->new ResourceNotFoundException(blog.toString(), FIELD, RESOURCE));
        
        try {
            Comment comment = new Comment();
          
            // blogRepository.findById(blogData.getId()).map(blogsData -> {
            //     comment.setBlog(blogsData);
            //     return commentRepository.save(comment);
            // }).orElseThrow(()->new ResourceNotFoundException(blog.toString(), FIELD, RESOURCE));
            Blog blogs = blogRepository.findById(blog)
            .orElseThrow(()-> new EntityNotFoundException("Blog not found."));


            comment.setBlog(blogs);
            comment.setGuest_email(request.getGuest_email());
            comment.setContent(request.getContent());  
            comment.setCreated_at(new Date());
            comment.setUpdated_at(new Date());
     
            commentRepository.save(comment);
            return fromEntity(comment);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public ResponseBaseDTO deleteById(Integer id, Integer blog) {
        try {
            Comment comment = commentRepository.findByIdAndBlogId(id, blog);
        
            if( commentRepository.findByIdAndBlogId(id, blog) == null) {
                return ResponseBaseDTO.error("401", "id " + id +" not found or blog id : " + blog + " not found");
            } else {
              
                commentRepository.delete(comment);
    
                return ResponseBaseDTO.ok(this.fromEntity(comment));
            }
           
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    // @Override
    // public ResponseTagsDTO update(Integer id, RequestTagsDTO request) {
    //     try {
    //         Tags tags = tagsRepository.findById(id).orElseThrow(
    //             ()->new ResourceNotFoundException(id.toString(), FIELD, RESOURCE)
    //         );   
            
    //         BeanUtils.copyProperties(request, tags);
    //         tags.setUpdated_at(new Date());
    //         tagsRepository.save(tags);

    //         return fromEntity(tags);
    //     } catch (ResourceNotFoundException e) {
    //         log.error(e.getMessage(), e);
    //         throw e;
    //     } catch (Exception e) {
    //         log.error(e.getMessage(), e);
    //         throw e;
    //     }
    // }

    private ResponseCommentDTO fromEntity(Comment comments) {
        ResponseCommentDTO response = new ResponseCommentDTO();
        BeanUtils.copyProperties(comments, response);
        return response;
    }
}