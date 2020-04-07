package com.blog.JavaSpringBoot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.JavaSpringBoot.common.MyPage;
import com.blog.JavaSpringBoot.common.MyPageable;
import com.blog.JavaSpringBoot.common.ResponseDto.ResponseBaseDTO;
import com.blog.JavaSpringBoot.common.util.PageConverter;
import com.blog.JavaSpringBoot.exeption.ResourceNotFoundException;

import javassist.NotFoundException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

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

    @GetMapping("/{blog}/commentsgetAll")
    public ResponseEntity<ResponseBase> getComment(@PathVariable Integer blog) {
        ResponseBase response = new ResponseBase<>();

        List<Comment> comment = commentRepository.findCommentByBlogIdone(blog);

        response.setData(comment);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

     //=================================================== With pagination ================================================

     @GetMapping("/{blog}/comments")
     public ResponseBaseDTO<MyPage<Comment>> getALl(@PathVariable Integer blog,
     MyPageable pageable, @RequestParam(required = false) String param, HttpServletRequest request
 ) { 
        try {
          
            Page<Comment> comments;
 
        if (param != null) {
            comments = commentService.findByEmail(MyPageable.convertToPageable(pageable), param);
        } else {
            comments = commentService.findAll(MyPageable.convertToPageable(pageable), blog);
        }
 
        PageConverter<Comment> converter = new PageConverter<>();
        String url = String.format("%s://%s:%d/posts/"+blog+"/comments",request.getScheme(),  request.getServerName(), request.getServerPort());

        String search = "";

        if(param != null){
            search += "&param="+param;
        }
 
        MyPage<Comment> respon = converter.convert(comments, url, search);

        return ResponseBaseDTO.ok(respon);
        
        } catch (Exception e) {

            return ResponseBaseDTO.error("200", e.getMessage());
        
        }

    }
 


    @PostMapping("/{blog}/comments/")
    public ResponseEntity<ResponseBase> postComment(@PathVariable Integer blog, @RequestBody Comment comment)
            throws NotFoundException {
        ResponseBase response = new ResponseBase<>();
   
        Blog blogData = blogRepository.findById(blog).orElseThrow(() -> new NotFoundException("Blog id " + blog + " NotFound"));
       
        // comment.setBlogs_id(blogData.getId());

        try {
        
           response.setData(blogRepository.findById(blog).map(blogs -> {
                comment.setBlogs_id(blogData.getId());
                return commentRepository.save(comment);
            }).orElseThrow(() -> new ResourceNotFoundException("Blog", "id", blog)));

            return new ResponseEntity<>(response ,HttpStatus.OK);
            
        } catch (Exception e) {
            response.setStatus(false);
            response.setCode(500);
            response.setMessage(e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        // return new ResponseEntity<>(response, HttpStatus.OK);
    }
    


    @PostMapping("/{blog}/comments")
    public ResponseEntity<ResponseBase> createBlogComment(@PathVariable Integer blog, @RequestBody Comment commentData) {
        
        ResponseBase response = new ResponseBase();

        Blog blogData = blogRepository.findById(blog).orElseThrow(() -> new ResourceNotFoundException("Blog", "id", blog));

        try {

            response.setData(blogRepository.findById(blogData.getId()).map(blogsData -> {
                commentData.setBlog(blogsData);
                return commentRepository.save(commentData);
            }).orElseThrow(() -> new ResourceNotFoundException("Blog", "id", blog)));

            return new ResponseEntity<>(response ,HttpStatus.OK);

        } catch (Exception e) {

            response.setStatus(false);
            response.setCode(500);
            response.setMessage(e.getMessage() );

            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);

        }

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

    //============================================= with pagination ===========================================================
    
}