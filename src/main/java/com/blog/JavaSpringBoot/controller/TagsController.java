package com.blog.JavaSpringBoot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javassist.NotFoundException;

import com.blog.JavaSpringBoot.model.Tags;
import com.blog.JavaSpringBoot.repository.TagsRepository;
import com.blog.JavaSpringBoot.service.TagsService;
import com.blog.JavaSpringBoot.exception.ResponseBase;
import com.blog.JavaSpringBoot.dto.response.ResponseBaseDTO;
import com.blog.JavaSpringBoot.dto.response.ResponseTagsDTO;
import com.blog.JavaSpringBoot.dto.request.RequestTagsDTO;
import com.blog.JavaSpringBoot.util.PageConverter;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.blog.JavaSpringBoot.config.MyPage;
import com.blog.JavaSpringBoot.config.MyPageable;

/**
 * TagsController
 */
@RestController
@RequestMapping("/tags")
public class TagsController {

    @Autowired
    TagsRepository tagsRepository;

    @Autowired
    TagsService tagsService;


    // @GetMapping("/getAll")
    // public ResponseEntity<ResponseBase> getTags() {
    //     ResponseBase response = new ResponseBase<>();

    //     response.setData(tagsRepository.findAll());

    //     return new ResponseEntity<>(response, HttpStatus.OK);
    // }

    // @GetMapping(value = "/{id}")
    // public ResponseEntity<ResponseBase> getTagsById(@PathVariable Integer id) throws NotFoundException {
    //     ResponseBase response = new ResponseBase<>();

    //     Tags tags = tagsRepository.findById(id).orElseThrow(() -> new NotFoundException("Categories id " + id + " NotFound"));

    //     response.setData(tags);

    //     return new ResponseEntity<>(response, HttpStatus.OK);
    // }

    // @PostMapping()
    // public ResponseEntity<ResponseBase> postTags(@RequestBody Tags tags) {
    //     ResponseBase response = new ResponseBase<>();

    //     try {
    //         response.setData(tagsRepository.save(tags));
            
    //     } catch (Exception e) {
    //         response.setStatus(false);
    //         response.setCode(500);
    //         response.setMessage(e.getMessage());

    //         return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
        
    //     return new ResponseEntity<>(response, HttpStatus.OK);
    // }

    // @PutMapping("/{id}")
    // public ResponseEntity<ResponseBase> putTags(@PathVariable Integer id, @RequestBody Tags tags) throws NotFoundException {
    //     ResponseBase response = new ResponseBase<>();

    //     tagsRepository.findById(id).orElseThrow(() -> new NotFoundException("Categories id " + id + " NotFound"));

    //     try {
    //         response.setData(tagsService.update(id, tags));
    //     } catch (Exception e) {
    //         response.setStatus(false);
    //         response.setCode(500);
    //         response.setMessage(e.getMessage());

    //         return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
        
    //     return new ResponseEntity<>(response, HttpStatus.OK);
    // }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<ResponseBase> deteleTags(@PathVariable Integer id) {
    //     ResponseBase response = new ResponseBase<>();

    //     try {
    //         tagsRepository.deleteById(id);
    //     } catch (Exception e) {
    //         response.setStatus(false);
    //         response.setCode(500);
    //         response.setMessage(e.getMessage());

    //         return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    //     }

    //     return new ResponseEntity<>(response, HttpStatus.OK);
    // }


    // @DeleteMapping()
    // public ResponseEntity<ResponseBase> deteleTagsById(@RequestBody Tags tags) throws NotFoundException {
    //     ResponseBase response = new ResponseBase<>();

    //     Tags tagss = tagsRepository.findById(tags.getId()).orElseThrow(() -> new NotFoundException("Categories id " + tags.getId() + " NotFound"));


    //     try {
    //         tagsRepository.deleteById(tagss.getId());
    //     } catch (Exception e) {
    //         response.setStatus(false);
    //         response.setCode(500);
    //         response.setMessage(e.getMessage());

    //         return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    //     }

    //     return new ResponseEntity<>(response, HttpStatus.OK);
    // }

    // ====================================================== With Pagination =====================================================


    @GetMapping
    public ResponseBaseDTO<MyPage<ResponseTagsDTO>> listTags(
        MyPageable pageable, @RequestParam(required = false) String param, HttpServletRequest request
    ) { 
       Page<ResponseTagsDTO> tags;

       if (param != null) {
           tags = tagsService.findByName(MyPageable.convertToPageable(pageable), param);
       } else {
           tags = tagsService.findAll(MyPageable.convertToPageable(pageable));
       }

       PageConverter<ResponseTagsDTO> converter = new PageConverter<>();
       String url = String.format("%s://%s:%d/tags",request.getScheme(),  request.getServerName(), request.getServerPort());

       String search = "";

       if(param != null){
           search += "&param="+param;
       }

       MyPage<ResponseTagsDTO> response = converter.convert(tags, url, search);

       return ResponseBaseDTO.ok(response);
    }    
    
    
    @PostMapping
    public ResponseEntity createAuthor(@Valid @RequestBody RequestTagsDTO request) {
        return tagsService.save(request);
    }
    // public ResponseBaseDTO createTags(@Valid @RequestBody RequestTagsDTO request) {
    //     return ResponseBaseDTO.ok(tagsService.save(request));
    // }

    @PutMapping("{id}")
    public ResponseEntity updateTag(
         @Valid @RequestBody RequestTagsDTO request, @PathVariable("id") Integer id
    ) {
       return tagsService.update(id, request);
      
    }


    @GetMapping("/{id}")
    public ResponseBaseDTO<ResponseTagsDTO> getOne(@PathVariable Integer id) {
        return ResponseBaseDTO.ok(tagsService.findById(id));
    }

    @DeleteMapping
    public ResponseEntity deleteTag(@RequestBody Tags tag) {        
       return tagsService.deleteById(tag.getId());
    }

}