package com.blog.JavaSpringBoot.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javassist.NotFoundException;

import com.blog.JavaSpringBoot.model.Blog;
import com.blog.JavaSpringBoot.repository.AuthorRepository;
import com.blog.JavaSpringBoot.repository.BlogRepository;
import com.blog.JavaSpringBoot.repository.CategoriesRepository;
import com.blog.JavaSpringBoot.repository.TagsRepository;
import com.blog.JavaSpringBoot.service.BlogService;
import com.blog.JavaSpringBoot.exception.ResponseBase;
import com.blog.JavaSpringBoot.dto.response.ResponseBaseDTO;
import com.blog.JavaSpringBoot.dto.response.ResponseBlogDTO;
import com.blog.JavaSpringBoot.dto.request.RequestBlogDTO;
import com.blog.JavaSpringBoot.dto.request.RequestBlogUpdateDTO;
import com.blog.JavaSpringBoot.util.PageConverter;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.blog.JavaSpringBoot.config.MyPage;
import com.blog.JavaSpringBoot.config.MyPageable;
// import com.blog.JavaSpringBoot.model.request.BlogDto;

/**
 * BlogController
 */

@RestController
@RequestMapping("/posts")
public class BlogController {
    @Autowired
    private BlogRepository blogRepository;

    // @Autowired
    // private AuthorRepository authorRepository;

    // @Autowired
    // private CategoriesRepository categoriesRepository;

    @Autowired
    private BlogService blogService;

    // @Autowired
    // private TagsRepository tagsRepository;

    // @GetMapping("/getAll")
    // public ResponseEntity<ResponseBase> getBlog() {
    //     ResponseBase response = new ResponseBase<>();

    //     response.setData(blogRepository.findAll());

    //     return new ResponseEntity<>(response, HttpStatus.OK);
    // }

    // @GetMapping(value = "/{id}")
    // public ResponseEntity<ResponseBase> getBlogById(@PathVariable Integer id) throws NotFoundException {
    //     ResponseBase response = new ResponseBase<>();

    //     Blog blog = blogRepository.findById(id).orElseThrow(() -> new NotFoundException("Tags id " + id + " NotFound"));

    //     response.setData(blog);

    //     return new ResponseEntity<>(response, HttpStatus.OK);
    // }

    // @PostMapping("/")
    // public ResponseEntity<ResponseBase> postBlog(@RequestBody Blog blog) throws NotFoundException {
    //     ResponseBase response = new ResponseBase<>();

    //     Author author = authorRepository.findById(blog.getAuthor_id()).orElseThrow(() -> new NotFoundException("Blog id " + blog.getAuthor_id() + " NotFound"));
    //     Categories categories = categoriesRepository.findById(blog.getCategories_id()).orElseThrow(() -> new NotFoundException("Category id " + blog.getCategories_id() + " NotFound"));
       
      
    //     List<String> tagtag = blog.getTags_name();
    //     ArrayList<Tags> tags = new ArrayList<Tags>();

    //     for (String tag : tagtag) {
    //         Tags val = tagsRepository.findByName(tag);
            
    //         if(val == null) { 
    //             Tags tagsData = new Tags();
    //             tagsData.setName(tag);
    //             tagsRepository.save(tagsData);
        
    //             Tags valTags = tagsRepository.findById(tagsData.getId()).orElseThrow(() -> new NotFoundException("Tags id " + tag + " NotFound"));
    //             tags.add(valTags);
    //             // tags.add(val);
    //         } else {
    //             // Tags valTasg = tagsRepository.findById(val.getId()).orElseThrow(() -> new NotFoundException("Tags id " + tag + " NotFound"));
    //             tags.add(val);
    //         }
            
    //     }

    //     blog.setAuthor(author);
    //     blog.setCategories(categories);
    //     blog.setTag(tags);

    //     try {
    //         response.setData(blogRepository.save(blog));
    //     } catch (Exception e) {
    //         response.setStatus(false);
    //         response.setCode(500);
    //         response.setMessage(e.getMessage());

    //         return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
        
    //     return new ResponseEntity<>(response, HttpStatus.OK);
    // }

    // @PutMapping("/{id}")
    // public ResponseEntity<ResponseBase> putBlog(@PathVariable Integer id, @RequestBody Blog blog) throws NotFoundException {
    //     ResponseBase response = new ResponseBase<>();

    //     Blog blogData =  blogRepository.findById(id).orElseThrow(() -> new NotFoundException("Blog id " + id + " NotFound"));

    //     try {
    //         blogData.setTitle(blog.getTitle());
    //         blogData.setContent(blog.getContent());

    //         response.setData(blogService.update(id, blogData));

    //     } catch (Exception e) {
    //         response.setStatus(false);
    //         response.setCode(500);
    //         response.setMessage(e.getMessage());

    //         return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
        
    //     return new ResponseEntity<>(response, HttpStatus.OK);
    // }


    // @DeleteMapping()
    // public ResponseEntity<ResponseBase> deleteBlogRequest(@RequestBody Blog blogData) throws NotFoundException {

    //     ResponseBase response = new ResponseBase();

    //     Blog blog = blogRepository.findById(blogData.getId()).orElseThrow(() -> new NotFoundException("Blog id " + blogData.getId() + " NotFound"));

    //     try {
            
    //         blogRepository.deleteById(blogData.getId());

    //         return new ResponseEntity<>(response, HttpStatus.OK);

    //     } catch (Exception e) {
            
    //         response.setStatus(false);
    //         response.setCode(500);
    //         response.setMessage(e.getMessage());

    //         return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);

    //     }

    // }

    //=============================================== With Pagination ======================================================
    @GetMapping
    public ResponseBaseDTO<MyPage<ResponseBlogDTO>> getALl(
        MyPageable pageable, @RequestParam(required = false) String title,  @RequestParam(required = false) Integer author_id, 
        @RequestParam(required = false) Integer category_id, @RequestParam(required = false) String tagName,
        HttpServletRequest request
    ) {
        Page<ResponseBlogDTO> blogs;

        if (title != null) {
            blogs = blogService.findByName(MyPageable.convertToPageable(pageable), title);
        } else if(author_id != null ){
            blogs = blogService.findByIdAuthor(MyPageable.convertToPageable(pageable), author_id);
        } else if(category_id != null) {
            blogs = blogService.findByIdCategory(MyPageable.convertToPageable(pageable), category_id);
        } else if(tagName != null) {
            blogs = blogService.findByTag(MyPageable.convertToPageable(pageable), tagName);
        }else {
            blogs = blogService.findAll(MyPageable.convertToPageable(pageable));
        }

       PageConverter<ResponseBlogDTO> converter = new PageConverter<>();
       String url = String.format("%s://%s:%d/posts",request.getScheme(),  request.getServerName(), request.getServerPort());

       String search = "";

        if(title != null){
            search += "&param="+title;
        } else if ( author_id != null ){
            search += "&param="+author_id;
        } else if( category_id != null ){
            search += "&param="+category_id;
        }else if( tagName != null ){
            search += "&param="+tagName;
        }
       MyPage<ResponseBlogDTO> response = converter.convert(blogs, url, search);

       return ResponseBaseDTO.ok(response);
    }    
    

    @GetMapping("/{id}")
    public ResponseBaseDTO<ResponseBlogDTO> getOne(@PathVariable Integer id) {
        return ResponseBaseDTO.ok(blogService.findById(id));
    }

      
    @PostMapping
    public ResponseBaseDTO createBlog(@Valid @RequestBody RequestBlogDTO request) {
       
        return ResponseBaseDTO.ok(blogService.save(request));
    }

    @PutMapping("{id}")
    public ResponseEntity updateBlog(@Valid @RequestBody RequestBlogUpdateDTO request, @PathVariable("id") Integer id
    ) {
        ResponseBaseDTO  response = this.blogService.updateBlog(id, request);
        if (response.getCode() != "200"){
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST );
        }

        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseBaseDTO deleteBlog(@RequestBody Blog blog) {
        try {

            blogService.deleteById(blog.getId());

            return ResponseBaseDTO.ok();

        } catch (Exception e) {

            return ResponseBaseDTO.error("400", "blog id : " + blog.getId() + " not found");

        }
    }

    
}