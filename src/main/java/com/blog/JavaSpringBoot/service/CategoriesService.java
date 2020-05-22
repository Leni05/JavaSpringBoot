package com.blog.JavaSpringBoot.service;

import com.blog.JavaSpringBoot.model.Categories;
import com.blog.JavaSpringBoot.repository.CategoriesRepository;

import com.blog.JavaSpringBoot.exception.ResponseBase;
import com.blog.JavaSpringBoot.dto.response.ResponseCategoriesDTO;
import com.blog.JavaSpringBoot.dto.request.RequestCategoriesDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

/**
 * CategoriesService
 */
public interface CategoriesService {

    Page<ResponseCategoriesDTO> findAll(Pageable pageable);

    ResponseEntity findById(Integer id);

    Page<ResponseCategoriesDTO> findByName(Pageable pageable, String param);   

    ResponseEntity save(RequestCategoriesDTO request);

    ResponseEntity update(Integer id, RequestCategoriesDTO request);

    ResponseEntity deleteById(Integer id);

     // ResponseTagsDTO findByName(String name);
    // @Autowired
    // CategoriesRepository categoriesRepository;

    // @Autowired
    // private DateTime dateTime;

    // private static final String RESOURCE = "Authors";
    // private static final String FIELD = "id";


    // public Categories update(Integer id, Categories categories) {
    //     categories.setId(id);

    //     return categoriesRepository.save(categories);
    // }

    // public Page<ResponseCategoriesDTO> findAll(Pageable pageable) {
    //     try {

    //         return categoriesRepository.findAll(pageable).map(this::fromEntity);

    //     } catch (Exception e) {

    //         log.error(e.getMessage(), e);
    //         throw e;
    //     }
    // }

    // public Page<ResponseCategoriesDTO> findCategories(Pageable pageable, String param) {

    //     try {
    //         param = param.toLowerCase();
    //         return categoriesRepository.findCategories(pageable, param).map(this::fromEntity);

    //     } catch (Exception e) {

    //         log.error(e.getMessage(), e);
    //         throw e;
    //     }
    // }

    // private ResponseCategoriesDTO fromEntity(Categories categories) {
    //     ResponseCategoriesDTO response = new ResponseCategoriesDTO();
    //     BeanUtils.copyProperties(categories, response);
    //     return response;
    // }  

    
}