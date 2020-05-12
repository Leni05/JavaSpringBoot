package com.blog.JavaSpringBoot.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javassist.NotFoundException;
import com.blog.JavaSpringBoot.model.Roles;
import com.blog.JavaSpringBoot.repository.RolesRepository;
import com.blog.JavaSpringBoot.service.RolesService;

import javax.management.relation.RelationNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.blog.JavaSpringBoot.config.MyPage;
import com.blog.JavaSpringBoot.config.MyPageable;
import com.blog.JavaSpringBoot.dto.response.ResponseBaseDTO;
import com.blog.JavaSpringBoot.dto.response.ResponseRolesDTO;
import com.blog.JavaSpringBoot.util.PageConverter;
import com.blog.JavaSpringBoot.exception.ResponseBase;
/**
 * RolesController
 */
@RestController
@RequestMapping("/roles")
public class RolesController {
    @Autowired
    RolesRepository rolesRepository;

    @Autowired
    RolesService rolesService;
    

    @GetMapping
    public ResponseBaseDTO<MyPage<ResponseRolesDTO>> listTags(
        MyPageable pageable, @RequestParam(required = false) String param, HttpServletRequest request
    ) { 
       Page<ResponseRolesDTO> roles;

       if (param != null) {
           roles = rolesService.findByName(MyPageable.convertToPageable(pageable), param);
       } else {
           roles = rolesService.findAll(MyPageable.convertToPageable(pageable));
       }

       PageConverter<ResponseRolesDTO> converter = new PageConverter<>();
       String url = String.format("%s://%s:%d/tags",request.getScheme(),  request.getServerName(), request.getServerPort());

       String search = "";

       if(param != null){
           search += "&param="+param;
       }

       MyPage<ResponseRolesDTO> response = converter.convert(roles, url, search);

       return ResponseBaseDTO.ok(response);
    }    
    
}