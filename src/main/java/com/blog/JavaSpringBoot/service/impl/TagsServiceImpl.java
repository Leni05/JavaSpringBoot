package com.blog.JavaSpringBoot.service.impl;

import java.util.Date;

import com.blog.JavaSpringBoot.dto.request.RequestTagsDTO;
import com.blog.JavaSpringBoot.dto.response.ResponseTagsDTO;
import com.blog.JavaSpringBoot.exception.ResourceNotFoundException;

import com.blog.JavaSpringBoot.repository.TagsRepository;
import com.blog.JavaSpringBoot.model.Tags;
import com.blog.JavaSpringBoot.service.TagsService;
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
public class TagsServiceImpl implements TagsService {
    
    @Autowired
    private TagsRepository tagsRepository;

    @Autowired
    private DateTime dateTime;

    private static final String RESOURCE = "Tags";
    private static final String FIELD = "id";

    @Override
    public ResponseTagsDTO deleteById(Integer id) {
        try {
            Tags tags = tagsRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(id.toString(), FIELD, RESOURCE));
            tagsRepository.deleteById(id);

            return fromEntity(tags);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
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

    // @Override
    // public ResponseTagsDTO findByName(String name) {
    //     try {
    //         Tags tag = tagsRepository.findByName(name);

    //         return fromEntity(tag);
    //     } catch (Exception e) {
    //         log.error(e.getMessage(), e);
    //         throw e;
    //     }
    // }
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
    public ResponseTagsDTO save(RequestTagsDTO request) {
        try {
            Tags tags = new Tags();

            tags.setName(request.getName());
            tags.setCreated_at(new Date());
            tags.setUpdated_at(new Date());
                        
            tagsRepository.save(tags);
            return fromEntity(tags);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }
    
    @Override
    public ResponseTagsDTO update(Integer id, RequestTagsDTO request) {
        try {
            Tags tags = tagsRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException(id.toString(), FIELD, RESOURCE)
            );   
            
            BeanUtils.copyProperties(request, tags);
            tags.setUpdated_at(new Date());
            tagsRepository.save(tags);

            return fromEntity(tags);
        } catch (ResourceNotFoundException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    private ResponseTagsDTO fromEntity(Tags tags) {
        ResponseTagsDTO response = new ResponseTagsDTO();
        BeanUtils.copyProperties(tags, response);
        return response;
    }
}