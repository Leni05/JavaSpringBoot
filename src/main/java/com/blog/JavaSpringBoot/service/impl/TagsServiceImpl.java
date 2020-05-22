package com.blog.JavaSpringBoot.service.impl;

import java.util.Date;

import com.blog.JavaSpringBoot.dto.request.RequestTagsDTO;
import com.blog.JavaSpringBoot.dto.response.ResponseDataDTO;
import com.blog.JavaSpringBoot.dto.response.ResponseTagsDTO;
import com.blog.JavaSpringBoot.exception.ResourceNotFoundException;

import com.blog.JavaSpringBoot.repository.TagsRepository;
import com.blog.JavaSpringBoot.model.Author;
import com.blog.JavaSpringBoot.model.Tags;
import com.blog.JavaSpringBoot.service.TagsService;
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
public class TagsServiceImpl implements TagsService {
    
    @Autowired
    private TagsRepository tagsRepository;

    @Autowired
    private DateTime dateTime;

    @Autowired
    private Environment env;

    private static final String RESOURCE = "Tags";
    private static final String FIELD = "id";

    @Override
    public ResponseEntity deleteById(Integer id) {
        ResponseDataDTO<ResponseTagsDTO> responseData = new ResponseDataDTO<>();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Author roleLogin = (Author) auth.getPrincipal();  
        Tags tags = tagsRepository.getById(id);
        try {
            if (!roleLogin.getRole().getId().equals(PropertiesUtil.getRoleSysAdminID(env))) {
            
                responseData = new ResponseDataDTO<ResponseTagsDTO>(false, 404, "Access denied", null);
                return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
            } 
            if (tags == null) {            
                responseData = new ResponseDataDTO<ResponseTagsDTO>(false, 404, "tags not found", null);
                return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
            }   
            ResponseTagsDTO response = new ResponseTagsDTO() ;
            response.setId(tags.getId());
            response.setName(tags.getName());
            response.setCreated_at(tags.getCreated_at());
            response.setUpdated_at(tags.getUpdated_at());

            tagsRepository.deleteById(id);

            responseData = new ResponseDataDTO<ResponseTagsDTO>(true, 200, "success", response);
            return ResponseEntity.ok(responseData);          
           

        } catch (Exception e) {
            responseData = new ResponseDataDTO<ResponseTagsDTO>(false, 404, "Delete Tags gagal", null);
            return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Page<ResponseTagsDTO> findAll(Pageable pageable) {
        try {
            return tagsRepository.findAll(pageable).map(this::fromEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public ResponseTagsDTO findById(Integer id) {
        try {
            Tags tags = tagsRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(id.toString(), FIELD, RESOURCE));
            
            return fromEntity(tags);
        } catch (ResourceNotFoundException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Page<ResponseTagsDTO> findByName(Pageable pageable, String param) {
        try {
            param = param.toLowerCase();
            return tagsRepository.findByName(pageable, param).map(this::fromEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public ResponseEntity save(RequestTagsDTO request) {
        ResponseDataDTO<ResponseTagsDTO> responseData = new ResponseDataDTO<>();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Author roleLogin = (Author) auth.getPrincipal();  
        try {
            if (!roleLogin.getRole().getId().equals(PropertiesUtil.getRoleSysAdminID(env))) {            
                responseData = new ResponseDataDTO<ResponseTagsDTO>(false, 404, "Access denied", null);
                return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
            } 

            Tags tags = new Tags();
            tags.setName(request.getName());
            tags.setCreated_at(new Date());
            tags.setUpdated_at(new Date());                        
            tagsRepository.save(tags);

            ResponseTagsDTO response = new ResponseTagsDTO() ;
            response.setId(tags.getId());
            response.setName(tags.getName());
            response.setCreated_at(tags.getCreated_at());
            response.setUpdated_at(tags.getUpdated_at());

            responseData = new ResponseDataDTO<ResponseTagsDTO>(true, 200, "success", response);
            return ResponseEntity.ok(responseData);

        } catch (Exception e) {
            responseData = new ResponseDataDTO<ResponseTagsDTO>(false, 404, "Create Tags gagal", null);
            return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
        }
    }
    
    @Override
    public ResponseEntity update(Integer id, RequestTagsDTO request) {
        ResponseDataDTO<ResponseTagsDTO> responseData = new ResponseDataDTO<>();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Author roleLogin = (Author) auth.getPrincipal();  
        Tags tags = tagsRepository.getById(id);
        try {
            if (!roleLogin.getRole().getId().equals(PropertiesUtil.getRoleSysAdminID(env))) {
            
                responseData = new ResponseDataDTO<ResponseTagsDTO>(false, 404, "Access denied", null);
                return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
            } 
            if (tags == null) {            
                responseData = new ResponseDataDTO<ResponseTagsDTO>(false, 404, "tags not found", null);
                return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
            }          
            
            BeanUtils.copyProperties(request, tags);
            tags.setUpdated_at(new Date());
            tagsRepository.save(tags);

       
            ResponseTagsDTO response = new ResponseTagsDTO() ;
            response.setId(tags.getId());
            response.setName(tags.getName());
            response.setCreated_at(tags.getCreated_at());
            response.setUpdated_at(tags.getUpdated_at());

            responseData = new ResponseDataDTO<ResponseTagsDTO>(true, 200, "success", response);
            return ResponseEntity.ok(responseData);

        } catch (Exception e) {
            responseData = new ResponseDataDTO<ResponseTagsDTO>(false, 404, "Update Tags gagal", null);
            return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
        }
    }

    private ResponseTagsDTO fromEntity(Tags tags) {
        ResponseTagsDTO response = new ResponseTagsDTO();
        BeanUtils.copyProperties(tags, response);
        return response;
    }
}