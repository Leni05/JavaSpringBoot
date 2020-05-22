package com.blog.JavaSpringBoot.service.impl;

import java.util.Date;

import com.blog.JavaSpringBoot.dto.request.RequestCategoriesDTO;
import com.blog.JavaSpringBoot.dto.response.ResponseCategoriesDTO;
import com.blog.JavaSpringBoot.dto.response.ResponseDataDTO;
import com.blog.JavaSpringBoot.exception.ResourceNotFoundException;

import com.blog.JavaSpringBoot.repository.CategoriesRepository;
import com.blog.JavaSpringBoot.model.Author;
import com.blog.JavaSpringBoot.model.Categories;
import com.blog.JavaSpringBoot.service.CategoriesService;
import com.blog.JavaSpringBoot.util.DateTime;
import com.blog.JavaSpringBoot.util.PropertiesUtil;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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
public class CategoriesServiceImpl implements CategoriesService {
    
    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private DateTime dateTime;

    @Autowired
    private Environment env;

    private static final String RESOURCE = "Categories";
    private static final String FIELD = "id";

    @Override
    public ResponseEntity deleteById(Integer id) {
        ResponseDataDTO<ResponseCategoriesDTO> responseData = new ResponseDataDTO<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Author roleLogin = (Author) auth.getPrincipal();  

        Categories categories = categoriesRepository.getById(id);
        try {
            if (!roleLogin.getRole().getId().equals(PropertiesUtil.getRoleSysAdminID(env))) {            
                responseData = new ResponseDataDTO<ResponseCategoriesDTO>(false, 404, "Access denied", null);
                return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
            }
            
            if(categories == null ){
                responseData = new ResponseDataDTO<ResponseCategoriesDTO>(false, 404, "category not found", null);
                return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
            }  
            
            ResponseCategoriesDTO response = new ResponseCategoriesDTO() ;
            response.setId(categories.getId());
            response.setName(categories.getName());
            response.setCreated_at(categories.getCreated_at());
            response.setUpdated_at(categories.getUpdated_at());
            categoriesRepository.deleteById(id);

            responseData = new ResponseDataDTO<ResponseCategoriesDTO>(true, 200, "success", response);
            return ResponseEntity.ok(responseData);

        } catch (Exception e) {
            responseData = new ResponseDataDTO<ResponseCategoriesDTO>(false, 404, "create data gagal", null);
            return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
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
    public ResponseEntity findById(Integer id) {
        ResponseDataDTO<ResponseCategoriesDTO> responseData = new ResponseDataDTO<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Author roleLogin = (Author) auth.getPrincipal();  

        Categories categories = categoriesRepository.getById(id);
        try {
            if (!roleLogin.getRole().getId().equals(PropertiesUtil.getRoleSysAdminID(env))) {            
                responseData = new ResponseDataDTO<ResponseCategoriesDTO>(false, 404, "Access denied", null);
                return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
            }
            
            if(categories == null ){
                responseData = new ResponseDataDTO<ResponseCategoriesDTO>(false, 404, "category not found", null);
                return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
            }  

            ResponseCategoriesDTO response = new ResponseCategoriesDTO() ;
            response.setId(categories.getId());
            response.setName(categories.getName());
            response.setCreated_at(categories.getCreated_at());
            response.setUpdated_at(categories.getUpdated_at());

            responseData = new ResponseDataDTO<ResponseCategoriesDTO>(true, 200, "success", response);
            return ResponseEntity.ok(responseData);
        } catch (Exception e) {
            responseData = new ResponseDataDTO<ResponseCategoriesDTO>(false, 404, "create data gagal", null);
            return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
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
    public ResponseEntity save(RequestCategoriesDTO request) {
        ResponseDataDTO<ResponseCategoriesDTO> responseData = new ResponseDataDTO<>();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Author roleLogin = (Author) auth.getPrincipal();  

        try {
            if (!roleLogin.getRole().getId().equals(PropertiesUtil.getRoleSysAdminID(env))) {            
                responseData = new ResponseDataDTO<ResponseCategoriesDTO>(false, 404, "Access denied", null);
                return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
            } 

            Categories categories = new Categories();

            categories.setName(request.getName());
            categories.setCreated_at(new Date());
            categories.setUpdated_at(new Date());           
            categoriesRepository.save(categories);
            // return fromEntity(categories);
            ResponseCategoriesDTO response = new ResponseCategoriesDTO() ;
            response.setId(categories.getId());
            response.setName(categories.getName());
            response.setCreated_at(categories.getCreated_at());
            response.setUpdated_at(categories.getUpdated_at());

            responseData = new ResponseDataDTO<ResponseCategoriesDTO>(true, 200, "success", response);
            return ResponseEntity.ok(responseData);

        } catch (Exception e) {
            responseData = new ResponseDataDTO<ResponseCategoriesDTO>(false, 404, "create data gagal", null);
            return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity update(Integer id, RequestCategoriesDTO request) {

        ResponseDataDTO<ResponseCategoriesDTO> responseData = new ResponseDataDTO<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Author roleLogin = (Author) auth.getPrincipal();  

        Categories categories = categoriesRepository.getById(id);
        try {
            if (!roleLogin.getRole().getId().equals(PropertiesUtil.getRoleSysAdminID(env))) {            
                responseData = new ResponseDataDTO<ResponseCategoriesDTO>(false, 404, "Access denied", null);
                return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
            }
            
            if(categories == null ){
                responseData = new ResponseDataDTO<ResponseCategoriesDTO>(false, 404, "category not found", null);
                return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
            }
            
            BeanUtils.copyProperties(request, categories);
            categories.setUpdated_at(new Date());
            categoriesRepository.save(categories);

            ResponseCategoriesDTO response = new ResponseCategoriesDTO() ;
            response.setId(categories.getId());
            response.setName(categories.getName());
            response.setCreated_at(categories.getCreated_at());
            response.setUpdated_at(categories.getUpdated_at());

            responseData = new ResponseDataDTO<ResponseCategoriesDTO>(true, 200, "success", response);
            return ResponseEntity.ok(responseData);
        } catch (Exception e) {
            responseData = new ResponseDataDTO<ResponseCategoriesDTO>(false, 404, "create data gagal", null);
            return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
        }
    }

    private ResponseCategoriesDTO fromEntity(Categories categories) {
        ResponseCategoriesDTO response = new ResponseCategoriesDTO();
        BeanUtils.copyProperties(categories, response);
        return response;
    }
}