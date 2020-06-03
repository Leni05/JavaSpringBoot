package com.blog.JavaSpringBoot.service.impl;

import java.util.Date;

import com.blog.JavaSpringBoot.dto.request.RequestTagsDTO;
import com.blog.JavaSpringBoot.dto.response.ResponseRolesDTO;
import com.blog.JavaSpringBoot.dto.response.ResponseTagsDTO;
import com.blog.JavaSpringBoot.exception.ResourceNotFoundException;

import com.blog.JavaSpringBoot.repository.RolesRepository;
import com.blog.JavaSpringBoot.repository.AuthorRepository;
import com.blog.JavaSpringBoot.repository.MenuRepository;
import com.blog.JavaSpringBoot.model.Author;
import com.blog.JavaSpringBoot.model.Menu;
import com.blog.JavaSpringBoot.model.Roles;
import com.blog.JavaSpringBoot.service.RolesService;
import com.blog.JavaSpringBoot.util.DateTime;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import javassist.NotFoundException;
/**
 * TagServiceImpl
 */
@Slf4j
@Service
public class RolesServiceImpl implements RolesService {

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private MenuRepository menuRepository;

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

    // @Override   
    // public boolean roleAccess(String url, String method) {

    //     Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    //     Author user = (Author) auth.getPrincipal();  

    //     //fnd id author
    //     Author authors = authorRepository.getById(user.getId());

    //     //find menu
    //     // Menu menu = menuRepository.getMenuLink(url);

    //     // if(menu == null ){
            
    //     //     throw new NotFoundException("menu");
           
    //     // }


    //     return true;
    // }
}