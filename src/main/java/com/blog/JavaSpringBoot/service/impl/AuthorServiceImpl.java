package com.blog.JavaSpringBoot.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

// import com.blog.JavaSpringBoot.config.ResourceServerConfig;
import com.blog.JavaSpringBoot.dto.request.RequestAuthorDTO;
import com.blog.JavaSpringBoot.dto.request.RequestAuthorPasswordDTO;
import com.blog.JavaSpringBoot.dto.request.RequestAuthorUpdateDTO;
import com.blog.JavaSpringBoot.dto.response.ResponseAuthorDTO;
import com.blog.JavaSpringBoot.dto.response.ResponseAuthorPasswordDTO;
import com.blog.JavaSpringBoot.dto.response.ResponseAuthorUpdateDTO;
import com.blog.JavaSpringBoot.exception.ResourceNotFoundException;

import com.blog.JavaSpringBoot.repository.AuthorRepository;
import com.blog.JavaSpringBoot.repository.RolesRepository;
import com.blog.JavaSpringBoot.model.Author;
import com.blog.JavaSpringBoot.model.Roles;
import com.blog.JavaSpringBoot.service.Authorservice;
import com.blog.JavaSpringBoot.util.DateTime;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import lombok.extern.slf4j.Slf4j;

/**
 * TagServiceImpl
 */
@Slf4j
@Service
public class AuthorServiceImpl implements Authorservice {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private DataSource dataSource;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private DateTime dateTime;

    private static final String RESOURCE = "Author";
    private static final String FIELD = "id";
    
    @Override
    public ResponseAuthorDTO deleteById(Integer id) {
        try {
            Author author = authorRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(id.toString(), FIELD, RESOURCE));
            authorRepository.deleteById(id);

            return fromEntity(author);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Page<ResponseAuthorDTO> findAll(Pageable pageable) {
        try {
            return authorRepository.findAll(pageable).map(this::fromEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public ResponseAuthorDTO findById(Integer id) {
        try {
            Author author = authorRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(id.toString(), FIELD, RESOURCE));

            return fromEntity(author);
        } catch (ResourceNotFoundException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Page<ResponseAuthorDTO> findByName(Pageable pageable, String param) {
        try {
            param = param.toLowerCase();
            return authorRepository.findAuthor(pageable, param).map(this::fromEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public ResponseAuthorDTO save(RequestAuthorDTO request) {
        try {
            Author author = new Author();
            Roles roles =  rolesRepository.findById(request.getRoles_id()).orElseThrow(
                ()->new ResourceNotFoundException(request.getRoles_id().toString(), FIELD, RESOURCE)
            );   
 
            author.setRoles_id(roles.getId());
            author.setFirst_name(request.getFirst_name());
            author.setLast_name(request.getLast_name());
            author.setUsername(request.getUsername());
            author.setPassword(passwordEncoder().encode(request.getPassword()));
            author.setCreated_at(new Date());
            author.setUpdated_at(new Date());

            authorRepository.save(author);
            return fromEntity(author);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public ResponseAuthorUpdateDTO update(Integer id, RequestAuthorUpdateDTO request) {
        try {
            Author author = authorRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(id.toString(), FIELD, RESOURCE));

            BeanUtils.copyProperties(request, author);
            author.setUpdated_at(new Date());
            authorRepository.save(author);

            return fromEntityUpdate(author);
        } catch (ResourceNotFoundException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public ResponseAuthorPasswordDTO updatePass(Integer id, RequestAuthorPasswordDTO request) {

        try {
            Author authors = authorRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(id.toString(), FIELD, RESOURCE));

            BeanUtils.copyProperties(request, authors);
            authors.setPassword(passwordEncoder().encode(request.getPassword()));
            authors.setUpdated_at(new Date());
            authorRepository.save(authors);

            return fromEntityPass(authors);
        } catch (ResourceNotFoundException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }

    }

    private ResponseAuthorDTO fromEntity(Author autuhor) {
        ResponseAuthorDTO response = new ResponseAuthorDTO();
        BeanUtils.copyProperties(autuhor, response);
        return response;
    }

    private ResponseAuthorPasswordDTO fromEntityPass(Author autuhor) {
        ResponseAuthorPasswordDTO response = new ResponseAuthorPasswordDTO();
        BeanUtils.copyProperties(autuhor, response);
        return response;
    }

    private ResponseAuthorUpdateDTO fromEntityUpdate(Author autuhor) {
        ResponseAuthorUpdateDTO response = new ResponseAuthorUpdateDTO();
        BeanUtils.copyProperties(autuhor, response);
        return response;
    }

    @Override
    public Author getByUsername(String username) {
        try {
            return authorRepository.getUserByUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}