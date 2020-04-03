package com.blog.JavaSpringBoot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javassist.NotFoundException;
import java.util.List;
import java.util.Optional;

import com.blog.JavaSpringBoot.exeption.ResponseBase;
import com.blog.JavaSpringBoot.model.Blog;
import com.blog.JavaSpringBoot.model.Comment;
import com.blog.JavaSpringBoot.repository.BlogRepository;
import com.blog.JavaSpringBoot.repository.CommentRepository;
import com.blog.JavaSpringBoot.service.CommentService;
/**
 * CommentController
 */
@RestController
@RequestMapping("/posts")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    // @Autowired
    // private CommentService commentService;

    // @Autowired
    // private BlogRepository blogRepository;

    @GetMapping(value = "/{blog}/comments")
    public ResponseEntity<ResponseBase> getComment(@PathVariable Integer blogId) {
        ResponseBase response = new ResponseBase<>();

        List<Comment> comment = commentRepository.findCommentByBlogId(blogId);

        // response.setData(commentRepository.findAll());
        response.setData(comment);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    // @GetMapping(value = "/{id}")
    // public ResponseEntity<ResponseBase> getCommentById(@PathVariable Integer id) throws NotFoundException {
    //     ResponseBase response = new ResponseBase<>();

    //     Comment comments = commentRepository.findById(id).orElseThrow(() -> new NotFoundException("Categories id " + id + " NotFound"));

    //     response.setData(comments);

    //     return new ResponseEntity<>(response, HttpStatus.OK);
    // }


    // @PostMapping("/{blogs}/comments")
    // public ResponseEntity<ResponseBase> postComment(@PathVariable Integer blogs, @RequestBody Comment comment)
    //         throws NotFoundException {
    //     ResponseBase response = new ResponseBase<>();
   
    //     Blog blogData = blogRepository.findById(blogs).orElseThrow(() -> new NotFoundException("Blog id " + blogs + " NotFound"));
       
    //     comment.setBlogs_id(blogData.getId());

    //     try {
        
    //         response.setData(commentRepository.save(comment));
            
    //     } catch (Exception e) {
    //         response.setStatus(false);
    //         response.setCode(500);
    //         response.setMessage(e.getMessage());

    //         return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
        
    //     return new ResponseEntity<>(response, HttpStatus.OK);
    // }


    
}