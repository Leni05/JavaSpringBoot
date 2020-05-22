package com.blog.JavaSpringBoot.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.blog.JavaSpringBoot.dto.request.RequestBlogDTO;
import com.blog.JavaSpringBoot.dto.request.RequestBlogUpdateDTO;
import com.blog.JavaSpringBoot.dto.response.ResponseBaseDTO;
import com.blog.JavaSpringBoot.dto.response.ResponseBlogDTO;
import com.blog.JavaSpringBoot.dto.response.ResponseDataDTO;
import com.blog.JavaSpringBoot.exception.ResourceNotFoundException;
import com.blog.JavaSpringBoot.repository.AuthorRepository;
import com.blog.JavaSpringBoot.repository.BlogRepository;
import com.blog.JavaSpringBoot.repository.CategoriesRepository;
import com.blog.JavaSpringBoot.repository.TagsRepository;
import com.blog.JavaSpringBoot.model.Author;
import com.blog.JavaSpringBoot.model.Blog;
import com.blog.JavaSpringBoot.model.Categories;
import com.blog.JavaSpringBoot.model.Tags;
import com.blog.JavaSpringBoot.service.BlogService;
import com.blog.JavaSpringBoot.util.DateTime;
import com.blog.JavaSpringBoot.util.PropertiesUtil;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * TagServiceImpl
 */
@Slf4j
@Service
public class BlogServiceImpl implements BlogService {
    
    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private TagsRepository tagsRepository;

    @Autowired
    private DateTime dateTime;

    @Autowired
    private Environment env;
    
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
    public Page<ResponseBlogDTO> findAll(Pageable pageable) {
        try {
            return blogRepository.findAll(pageable).map(this::fromEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

  
    @Override
    public ResponseBlogDTO findById(Integer id) {
        try {
            Blog blogs = blogRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(id.toString(), FIELD, RESOURCE));
            
            return fromEntity(blogs);
        } catch (ResourceNotFoundException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Page<ResponseBlogDTO> findByName(Pageable pageable, String param) {
        try {
            param = param.toLowerCase();
            return blogRepository.search(pageable, param).map(this::fromEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }


    @Override
    public Page<ResponseBlogDTO> findByIdCategory(Pageable pageable, Integer categoryId) {
        try {
            return blogRepository.findByIdCategory(pageable, categoryId).map(this::fromEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Page<ResponseBlogDTO> findByIdAuthor(Pageable pageable, Integer authorId) {
        try {
            return blogRepository.findByIdAuthor(pageable, authorId).map(this::fromEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Page<ResponseBlogDTO> findByTag(Pageable pageable, String tagName) {
        try {
            return blogRepository.findByTag(pageable, tagName).map(this::fromEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }


    @Override
    public ResponseEntity save (RequestBlogDTO request) {

        ResponseDataDTO<ResponseBlogDTO> responseData = new ResponseDataDTO<>();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Author roleLogin = (Author) auth.getPrincipal();   

        Author author = authorRepository.getById(request.getAuthor_id());
        Categories categories = categoriesRepository.getById(request.getCategories_id());

        if( author == null ) {
            responseData = new ResponseDataDTO<ResponseBlogDTO>(false, 404, "data author tidak ditemukan", null);
            return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
        }

        if( categories == null ) {
            responseData = new ResponseDataDTO<ResponseBlogDTO>(false, 404, "data categories tidak ditemukan", null);
            return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
        }

        if( roleLogin.getId() == author.getId() || roleLogin.getRole().getId().equals(PropertiesUtil.getRoleSysAdminID(env)) ){     
                
            List<String> tagtag = request.getTags_name();
            ArrayList<Tags> tags = new ArrayList<Tags>();

            for (String tag : tagtag) {
                Tags val = tagsRepository.findByName(tag);
                
                if(val == null) { 
                    Tags tagsData = new Tags();
                    tagsData.setName(tag);
                    tagsRepository.save(tagsData);
            
                    Tags valTags = tagsRepository.findById(tagsData.getId()).orElseThrow(
                        ()->new ResourceNotFoundException(tagsData.getId().toString(), FIELD, RESOURCE)
                    );   
                    tags.add(valTags);
                
                } else {
                tags.add(val);
                }
                
            }

            try {         
                Blog blogs = new Blog();

                blogs.setAuthor((author));
                blogs.setCategories(categories);
                blogs.setTag(tags);
                blogs.setTitle(request.getTitle());
                blogs.setContent(request.getContent());
                blogs.setCreated_at(new Date());
                blogs.setUpdated_at(new Date());
                blogRepository.save(blogs);

                ResponseBlogDTO response = new ResponseBlogDTO() ;
                response.setId(blogs.getId());
                response.setAuthor(blogs.getAuthor());
                response.setCategories(blogs.getCategories());
                response.setTag(blogs.getTag());
                response.setTitle(blogs.getTitle());
                response.setContent(blogs.getContent());
                response.setCreated_at(blogs.getCreated_at());
                response.setUpdated_at(blogs.getUpdated_at());

                responseData = new ResponseDataDTO<ResponseBlogDTO>(true, 200, "success", response);
                return ResponseEntity.ok(responseData);
            

            } catch (Exception e) {
                responseData = new ResponseDataDTO<ResponseBlogDTO>(false, 404, "save data gagal", null);
                return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
            }        
        } else {
            responseData = new ResponseDataDTO<ResponseBlogDTO>(false, 404, "access denied", null);
            return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
        }     
      
    }
    
    @Override
    public ResponseEntity updateBlog(Integer id, RequestBlogUpdateDTO request) {

        ResponseDataDTO<ResponseBlogDTO> responseData = new ResponseDataDTO<>();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
        Author roleLogin = authorRepository.findByUsername(auth.getName());
        Blog blogs = blogRepository.getById(id);

        Author authorData = authorRepository.getByIdBlog(id);

        System.out.println("masuk id blogss  : "+ blogs + "id request : " +id);

        if( blogs == null) {
            responseData = new ResponseDataDTO<ResponseBlogDTO>(false, 404, "data blog tidak ditemukan", null);
            return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
        }

        if( roleLogin.getId() == authorData.getId() || roleLogin.getRole().getId().equals(PropertiesUtil.getRoleSysAdminID(env)) ){         

            try {         
                blogs.setTitle(request.getTitle());
                blogs.setContent(request.getContent());
                blogs.setUpdated_at(new Date());
                blogRepository.save(blogs);

                ResponseBlogDTO response = new ResponseBlogDTO();
                response.setId(blogs.getId());
                response.setAuthor(blogs.getAuthor());
                response.setCategories(blogs.getCategories());
                response.setTag(blogs.getTag());
                response.setTitle(blogs.getTitle());
                response.setContent(blogs.getContent());
                response.setCreated_at(blogs.getCreated_at());
                response.setUpdated_at(blogs.getUpdated_at());

                responseData = new ResponseDataDTO<ResponseBlogDTO>(true, 200, "success", response);
                return ResponseEntity.ok(responseData);
            } catch (Exception e) {
                responseData = new ResponseDataDTO<ResponseBlogDTO>(false, 404, "gagal update data", null);
                return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
            }
             
        } else {
            responseData = new ResponseDataDTO<ResponseBlogDTO>(false, 404, "access denied", null);
            return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
        }
          
    }


    @Override
    public ResponseEntity deleteById(Integer id) {
        ResponseDataDTO<ResponseBlogDTO> responseData = new ResponseDataDTO<>();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
        Author roleLogin = (Author) auth.getPrincipal(); 

        Blog blogs = blogRepository.getById(id);

        Author authorData = authorRepository.getByIdBlog(id);
        System.out.println("masuk id blogss  : "+ blogs + "id request : " +id);
        if( blogs == null) {
            System.out.println("masuk null");
            responseData = new ResponseDataDTO<ResponseBlogDTO>(false, 404, "data blog tidak ditemukan", null);
            return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
        }

        if( roleLogin.getId() == authorData.getId() || roleLogin.getRole().getId().equals(PropertiesUtil.getRoleSysAdminID(env)) ){         
            try {

                ResponseBlogDTO response = new ResponseBlogDTO() ;

                response.setId(blogs.getId());
                response.setAuthor(blogs.getAuthor());
                response.setCategories(blogs.getCategories());
                response.setTag(blogs.getTag());
                response.setTitle(blogs.getTitle());
                response.setContent(blogs.getContent());
                response.setCreated_at(blogs.getCreated_at());
                response.setUpdated_at(blogs.getUpdated_at());
    
                blogRepository.deleteById(id);            
                responseData = new ResponseDataDTO<ResponseBlogDTO>(true, 200, "success", response);
                return ResponseEntity.ok(responseData);
    
            } catch (Exception e) {
                responseData = new ResponseDataDTO<ResponseBlogDTO>(false, 404, "gagal update data", null);
                return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
    
            }
     
        } else {

            responseData = new ResponseDataDTO<ResponseBlogDTO>(false, 404, "hanya bisa delete blog diri sendiri", null);
            return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
        } 
    }



    private ResponseBlogDTO fromEntity(Blog blogs) {
        ResponseBlogDTO response = new ResponseBlogDTO();
        BeanUtils.copyProperties(blogs, response);
        return response;
    }
}