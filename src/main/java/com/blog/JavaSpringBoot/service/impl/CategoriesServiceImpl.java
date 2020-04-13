package com.blog.JavaSpringBoot.service.impl;

import java.util.Date;

import com.blog.JavaSpringBoot.dto.request.RequestCategoriesDTO;
import com.blog.JavaSpringBoot.dto.response.ResponseCategoriesDTO;
import com.blog.JavaSpringBoot.exception.ResourceNotFoundException;

import com.blog.JavaSpringBoot.repository.CategoriesRepository;
import com.blog.JavaSpringBoot.model.Categories;
import com.blog.JavaSpringBoot.service.CategoriesService;
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
public class CategoriesServiceImpl implements CategoriesService {
    
    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private DateTime dateTime;

    private static final String RESOURCE = "Categories";
    private static final String FIELD = "id";

    @Override
    public ResponseCategoriesDTO deleteById(Integer id) {
        try {
            Categories categories = categoriesRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(id.toString(), FIELD, RESOURCE));
            categoriesRepository.deleteById(id);

            return fromEntity(categories);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Page<ResponseCategoriesDTO> findAll(Pageable pageable) {
        try {
            return categoriesRepository.findAll(pageable).map(this::fromEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    // @Override
    // public ResponseCategoriesDTO findByName(String name) {
    //     try {
    //         Categories categories = categoriesRepository.findByName(name);

    //         return fromEntity(tag);
    //     } catch (Exception e) {
    //         log.error(e.getMessage(), e);
    //         throw e;
    //     }
    // }

    @Override
    public ResponseCategoriesDTO findById(Integer id) {
        try {
            Categories categories = categoriesRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(id.toString(), FIELD, RESOURCE));
            
            return fromEntity(categories);
        } catch (ResourceNotFoundException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Page<ResponseCategoriesDTO> findByName(Pageable pageable, String param) {
        try {
            param = param.toLowerCase();
            return categoriesRepository.findCategories(pageable, param).map(this::fromEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public ResponseCategoriesDTO save(RequestCategoriesDTO request) {
        try {
            Categories categories = new Categories();

            categories.setName(request.getName());
            categories.setCreated_at(new Date());
            categories.setUpdated_at(new Date());

            
            categoriesRepository.save(categories);
            return fromEntity(categories);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public ResponseCategoriesDTO update(Integer id, RequestCategoriesDTO request) {
        try {
            Categories categories = categoriesRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException(id.toString(), FIELD, RESOURCE)
            );   
            
            BeanUtils.copyProperties(request, categories);
            categories.setUpdated_at(new Date());
            categoriesRepository.save(categories);

            return fromEntity(categories);
        } catch (ResourceNotFoundException e) {
            log.error(e.getMessage(), e);
            throw e;
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