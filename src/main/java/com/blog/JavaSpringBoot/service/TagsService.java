package com.blog.JavaSpringBoot.service;


import com.blog.JavaSpringBoot.model.Tags;
import com.blog.JavaSpringBoot.repository.TagsRepository;

import com.blog.JavaSpringBoot.exception.ResponseBase;
import com.blog.JavaSpringBoot.dto.response.ResponseTagsDTO;
import com.blog.JavaSpringBoot.dto.request.RequestTagsDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * TagServiceImpl
 */
public interface TagsService {

    Page<ResponseTagsDTO> findAll(Pageable pageable);

    ResponseTagsDTO findById(Integer id);

    Page<ResponseTagsDTO> findByName(Pageable pageable, String param);

    // ResponseTagsDTO findByName(String name);

    ResponseTagsDTO save(RequestTagsDTO request);

    ResponseTagsDTO update(Integer id, RequestTagsDTO request);

    ResponseTagsDTO deleteById(Integer id);
    

    // @Autowired
    // private TagsRepository tagsRepository;

    // @Autowired
    // private DateTime dateTime;

    // private static final String RESOURCE = "Tags";
    // private static final String FIELD = "id";

    // public Tags update(Integer id, Tags tags) {
    //     tags.setId(id);

    //     return tagsRepository.save(tags);
    // }

    // public Page<ResponseTagsDTO> findAll(Pageable pageable) {
    //     try {

    //         return tagsRepository.findAll(pageable).map(this::fromEntity);

    //     } catch (Exception e) {

    //         log.error(e.getMessage(), e);
    //         throw e;
    //     }
    // }

    // public Page<ResponseTagsDTO> findByNameParams(Pageable pageable, String param) {

    //     try {
    //         param = param.toLowerCase();
    //         return tagsRepository.findByNameParams(pageable, param).map(this::fromEntity);

    //     } catch (Exception e) {

    //         log.error(e.getMessage(), e);
    //         throw e;
    //     }
    // }

    // private ResponseTagsDTO fromEntity(Tags tags) {
    //     ResponseTagsDTO response = new ResponseTagsDTO();
    //     BeanUtils.copyProperties(tags, response);
    //     return response;
    // }
}