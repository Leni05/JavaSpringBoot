package com.blog.JavaSpringBoot.service;


import com.blog.JavaSpringBoot.model.Roles;
import com.blog.JavaSpringBoot.repository.RolesRepository;

import com.blog.JavaSpringBoot.exception.ResponseBase;
import com.blog.JavaSpringBoot.dto.response.ResponseRolesDTO;
import com.blog.JavaSpringBoot.dto.request.RequestTagsDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * RolesServiceImpl
 */
public interface RolesService {

    Page<ResponseRolesDTO> findAll(Pageable pageable);

    ResponseRolesDTO findById(Integer id);

    Page<ResponseRolesDTO> findByName(Pageable pageable, String param);
    
}