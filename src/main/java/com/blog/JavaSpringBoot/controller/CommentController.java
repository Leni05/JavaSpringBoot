package com.blog.JavaSpringBoot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.blog.JavaSpringBoot.exeption.ResourceNotFoundException;

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

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogRepository blogRepository;

    @GetMapping("/{blog}/comments")
    public ResponseEntity<ResponseBase> getComment(@PathVariable Integer blog) {
        ResponseBase response = new ResponseBase<>();

        List<Comment> comment = commentRepository.findCommentByBlogId(blog);

        response.setData(comment);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/{blog}/comments")
    public ResponseEntity<ResponseBase> postComment(@PathVariable Integer blog, @RequestBody Comment comment)
            throws NotFoundException {
        ResponseBase response = new ResponseBase<>();
   
        Blog blogData = blogRepository.findById(blog).orElseThrow(() -> new NotFoundException("Blog id " + blog + " NotFound"));
       
        comment.setBlogs_id(blogData.getId());

        try {
        
            response.setData(commentRepository.save(comment));
            
        } catch (Exception e) {
            response.setStatus(false);
            response.setCode(500);
            response.setMessage(e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    

    @GetMapping("/{blog}/comments/{id}")
    public ResponseEntity<ResponseBase> getCommentByIdAndBlogId( @PathVariable Integer blog, @PathVariable Integer id) throws NotFoundException {
        ResponseBase response = new ResponseBase<>();

        Blog blogs = blogRepository.findById(blog).orElseThrow(() -> new ResourceNotFoundException("Blog", "id", blog));

        Comment comment = commentRepository.findByIdAndBlogId(id, blog);

        response.setData(comment);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    // @PutMapping("/{blogId}/comments/{commentId}")
    // public ResponseEntity<ResponseBase> updateBlogComment(@PathVariable Long blogId, @PathVariable Long commentId, @RequestBody Comment commentData) {

    //     ResponseBase response = new ResponseBase()<>();

    //     try {
    //         response.setData(commentRepository.findById(commentId).map(comment -> {
    //             comment.setContent(commentData.getContent());
    //             return commentRepository.save(comment);
    //         }).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId)));

    //         return new ResponseEntity<>(response, HttpStatus.OK);

    //     } catch (Exception e) {
    //         response.setStatus(false);
    //         response.setCode(500);
    //         response.setMessage(e.getMessage());

    //         return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    //     }

    // } 

    @DeleteMapping("/{blog}/comments")
    public ResponseEntity<ResponseBase> deleteCommentRequest(@PathVariable Integer blog, @RequestBody Comment comments) {
        ResponseBase response = new ResponseBase<>();

        Blog blogs = blogRepository.findById(blog).orElseThrow(() -> new ResourceNotFoundException("Blog", "id", blog));
       
        try {

            commentRepository.deleteByIdAndBlogId(comments.getId(), blog);

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.setStatus(false);
            response.setCode(500);
            response.setMessage(e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    
}