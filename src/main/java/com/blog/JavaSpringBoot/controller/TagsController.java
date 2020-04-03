package com.blog.JavaSpringBoot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javassist.NotFoundException;

import com.blog.JavaSpringBoot.model.Tags;
import com.blog.JavaSpringBoot.repository.TagsRepository;
import com.blog.JavaSpringBoot.service.TagsService;
import com.blog.JavaSpringBoot.exeption.ResponseBase;

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

    @GetMapping()
    public ResponseEntity<ResponseBase> getTags() {
        ResponseBase response = new ResponseBase<>();

        response.setData(tagsRepository.findAll());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ResponseBase> getTagsById(@PathVariable Integer id) throws NotFoundException {
        ResponseBase response = new ResponseBase<>();

        Tags tags = tagsRepository.findById(id).orElseThrow(() -> new NotFoundException("Categories id " + id + " NotFound"));

        response.setData(tags);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping()
    public ResponseEntity<ResponseBase> postTags(@RequestBody Tags tags) {
        ResponseBase response = new ResponseBase<>();

        try {
            response.setData(tagsRepository.save(tags));
            
        } catch (Exception e) {
            response.setStatus(false);
            response.setCode(500);
            response.setMessage(e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBase> putTags(@PathVariable Integer id, @RequestBody Tags tags) throws NotFoundException {
        ResponseBase response = new ResponseBase<>();

        tagsRepository.findById(id).orElseThrow(() -> new NotFoundException("Categories id " + id + " NotFound"));

        try {
            response.setData(tagsService.update(id, tags));
        } catch (Exception e) {
            response.setStatus(false);
            response.setCode(500);
            response.setMessage(e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBase> deteleTags(@PathVariable Integer id) {
        ResponseBase response = new ResponseBase<>();

        try {
            tagsRepository.deleteById(id);
        } catch (Exception e) {
            response.setStatus(false);
            response.setCode(500);
            response.setMessage(e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping()
    public ResponseEntity<ResponseBase> deteleTagsById(@RequestBody Tags tags) throws NotFoundException {
        ResponseBase response = new ResponseBase<>();

        Tags tagss = tagsRepository.findById(tags.getId()).orElseThrow(() -> new NotFoundException("Categories id " + tags.getId() + " NotFound"));


        try {
            tagsRepository.deleteById(tagss.getId());
        } catch (Exception e) {
            response.setStatus(false);
            response.setCode(500);
            response.setMessage(e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    
}