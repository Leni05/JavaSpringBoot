package com.blog.JavaSpringBoot.service;


import com.blog.JavaSpringBoot.model.Categories;
import com.blog.JavaSpringBoot.repository.CategoriesRepository;

import com.blog.JavaSpringBoot.exeption.ResponseBase;
import com.blog.JavaSpringBoot.common.ResponseDto.ResponseCategoriesDTO;
import com.blog.JavaSpringBoot.common.util.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
/**
 * CategoriesService
 */
@Slf4j
@Service
public class CategoriesService {
    @Autowired
    CategoriesRepository categoriesRepository;

    @Autowired
    private DateTime dateTime;

    private static final String RESOURCE = "Authors";
    private static final String FIELD = "id";


    public Categories update(Integer id, Categories categories) {
        categories.setId(id);

        return categoriesRepository.save(categories);
    }

    public Page<ResponseCategoriesDTO> findAll(Pageable pageable) {
        try {

            return categoriesRepository.findAll(pageable).map(this::fromEntity);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw e;
        }
    }

    public Page<ResponseCategoriesDTO> findCategories(Pageable pageable, String param) {

        try {
            param = param.toLowerCase();
            return categoriesRepository.findCategories(pageable, param).map(this::fromEntity);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw e;
        }
    }

    private ResponseCategoriesDTO fromEntity(Categories categories) {
        ResponseCategoriesDTO response = new ResponseCategoriesDTO();
        BeanUtils.copyProperties(categories, response);
        return response;
    }

   

    
}