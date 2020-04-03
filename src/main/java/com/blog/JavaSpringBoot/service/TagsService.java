package com.blog.JavaSpringBoot.service;

import com.blog.JavaSpringBoot.exeption.ResponseBase;
import com.blog.JavaSpringBoot.common.ResponseDto.ResponseTagsDTO;
// import com.blog.demo.common.exception.ResourceNotFoundException;
import com.blog.JavaSpringBoot.common.util.DateTime;
import com.blog.JavaSpringBoot.model.Tags;
import com.blog.JavaSpringBoot.repository.TagsRepository;

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
public class TagsService {

    @Autowired
    private TagsRepository tagsRepository;

    @Autowired
    private DateTime dateTime;

    private static final String RESOURCE = "Tags";
    private static final String FIELD = "id";

    public Tags update(Integer id, Tags tags) {
        tags.setId(id);

        return tagsRepository.save(tags);
    }

    public Page<ResponseTagsDTO> findAll(Pageable pageable) {
        try {

            return tagsRepository.findAll(pageable).map(this::fromEntity);

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            throw e;
        }
    }

    public Page<ResponseTagsDTO> findByNameParams(Pageable pageable, String param) {

        try {
            param = param.toLowerCase();
            return tagsRepository.findByNameParams(pageable, param).map(this::fromEntity);

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