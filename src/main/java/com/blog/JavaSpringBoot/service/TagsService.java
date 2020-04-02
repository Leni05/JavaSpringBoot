package com.blog.JavaSpringBoot.service;

import com.blog.JavaSpringBoot.model.Tags;
import com.blog.JavaSpringBoot.repository.TagsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * TagsService
 */
@Service
public class TagsService {

    @Autowired
    private TagsRepository tagsRepository;

    public Tags update(Integer id, Tags tags) {
        tags.setId(id);

        return tagsRepository.save(tags);
    }
}