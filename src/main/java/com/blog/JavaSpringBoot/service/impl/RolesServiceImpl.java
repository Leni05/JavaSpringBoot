package com.blog.JavaSpringBoot.service.impl;


import java.util.Date;

import com.blog.JavaSpringBoot.dto.request.RequestTagsDTO;
import com.blog.JavaSpringBoot.dto.response.ResponseRolesDTO;
import com.blog.JavaSpringBoot.dto.response.ResponseTagsDTO;
import com.blog.JavaSpringBoot.exception.ResourceNotFoundException;

import com.blog.JavaSpringBoot.repository.RolesRepository;
import com.blog.JavaSpringBoot.model.Roles;
import com.blog.JavaSpringBoot.service.RolesService;
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
public class RolesServiceImpl implements RolesService {

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private DateTime dateTime;

    private static final String RESOURCE = "Tags";
    private static final String FIELD = "id";

    @Override
    public Page<ResponseRolesDTO> findAll(Pageable pageable) {
         try {
            return rolesRepository.findAll(pageable).map(this::fromEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public ResponseRolesDTO findById(Integer id) {
        try {
            Roles tags = rolesRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(id.toString(), FIELD, RESOURCE));
            
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
    public Page<ResponseRolesDTO> findByName(Pageable pageable, String param) {
        // TODO Auto-generated method stub
        return null;
    }
    

    private ResponseRolesDTO fromEntity(Roles roles) {
        ResponseRolesDTO response = new ResponseRolesDTO();
        BeanUtils.copyProperties(roles, response);
        return response;
    }
}