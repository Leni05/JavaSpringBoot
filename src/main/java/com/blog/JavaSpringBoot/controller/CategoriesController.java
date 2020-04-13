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
import com.blog.JavaSpringBoot.model.Categories;
import com.blog.JavaSpringBoot.repository.CategoriesRepository;
import com.blog.JavaSpringBoot.service.CategoriesService;

import javax.management.relation.RelationNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.blog.JavaSpringBoot.config.MyPage;
import com.blog.JavaSpringBoot.config.MyPageable;
import com.blog.JavaSpringBoot.dto.response.ResponseBaseDTO;
import com.blog.JavaSpringBoot.dto.response.ResponseCategoriesDTO;
import com.blog.JavaSpringBoot.dto.request.RequestCategoriesDTO;
import com.blog.JavaSpringBoot.util.PageConverter;
import com.blog.JavaSpringBoot.exception.ResponseBase;


/**
 * CategoriesController
 */
@RestController
@RequestMapping("/categories")
public class CategoriesController {

    @Autowired
    CategoriesRepository categoriesRepository;

    @Autowired
    CategoriesService categoriesService;

    @GetMapping("/getAll")
    public ResponseEntity<ResponseBase> getCategories() {
        ResponseBase response = new ResponseBase<>();

        response.setData(categoriesRepository.findAll());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // @GetMapping(value = "/{id}")
    // public ResponseEntity<ResponseBase> getCategoriesById(@PathVariable Integer id) throws NotFoundException {
    //     ResponseBase response = new ResponseBase<>();

    //     Categories categories = categoriesRepository.findById(id).orElseThrow(() -> new NotFoundException("Categories id " + id + " NotFound"));

    //     response.setData(categories);

    //     return new ResponseEntity<>(response, HttpStatus.OK);
    // }


    // @PostMapping()
    // public ResponseEntity<ResponseBase> postCategories(@RequestBody Categories categories) {
    //     ResponseBase response = new ResponseBase<>();

    //     try {
    //         response.setData(categoriesRepository.save(categories));
            
    //     } catch (Exception e) {
    //         response.setStatus(false);
    //         response.setCode(500);
    //         response.setMessage(e.getMessage());

    //         return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
        
    //     return new ResponseEntity<>(response, HttpStatus.OK);
    // }


    // @PutMapping("/{id}")
    // public ResponseEntity<ResponseBase> putCategories(@PathVariable Integer id, @RequestBody Categories categories) throws NotFoundException {
    //     ResponseBase response = new ResponseBase<>();

    //     categoriesRepository.findById(id).orElseThrow(() -> new NotFoundException("Categories id " + id + " NotFound"));

    //     try {
    //         response.setData(categoriesService.update(id, categories));
    //     } catch (Exception e) {
    //         response.setStatus(false);
    //         response.setCode(500);
    //         response.setMessage(e.getMessage());

    //         return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
        
    //     return new ResponseEntity<>(response, HttpStatus.OK);
    // }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<ResponseBase> deleteCategories(@PathVariable Integer id) {
    //     ResponseBase response = new ResponseBase<>();

    //     try {
    //         categoriesRepository.deleteById(id);
    //     } catch (Exception e) {
    //         response.setStatus(false);
    //         response.setCode(500);
    //         response.setMessage(e.getMessage());

    //         return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    //     }

    //     return new ResponseEntity<>(response, HttpStatus.OK);
    // }


    // @DeleteMapping()
    // public ResponseEntity<ResponseBase> deleteCategories(@RequestBody Categories categoriesData) throws NotFoundException {
        
    //     ResponseBase response = new ResponseBase<>();
    //     Categories category = categoriesRepository.findById(categoriesData.getId()).orElseThrow(() -> new NotFoundException("Categories id " + categoriesData.getId() + " NotFound"));

    //     try {

    //         //response.setData(categoriesRepository.deleteById(categoryId));
    //         categoriesRepository.deleteById(category.getId());
    //     } catch (Exception e) {
    //         response.setStatus(false);
    //         response.setCode(500);
    //         response.setMessage(e.getMessage());

    //         return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    //     }

    //     return new ResponseEntity<>(response, HttpStatus.OK);
    // }

    
    // ====================================================== With Pagination =====================================================

    @GetMapping
    public ResponseBaseDTO<MyPage<ResponseCategoriesDTO>> listCategories(
        MyPageable pageable, @RequestParam(required = false) String param, HttpServletRequest request
    ) { 
        Page<ResponseCategoriesDTO> categories;

        if (param != null) {
            categories = categoriesService.findByName(MyPageable.convertToPageable(pageable), param);
        } else {
            categories = categoriesService.findAll(MyPageable.convertToPageable(pageable));
        }

        PageConverter<ResponseCategoriesDTO> converter = new PageConverter<>();
        String url = String.format("%s://%s:%d/categories",request.getScheme(),  request.getServerName(), request.getServerPort());

        String search = "";

        if(param != null){
            search += "&param="+param;
        }

        MyPage<ResponseCategoriesDTO> response = converter.convert(categories, url, search);

        return ResponseBaseDTO.ok(response);
    }    
    
     
    @PostMapping
    public ResponseBaseDTO createCategories(@Valid @RequestBody RequestCategoriesDTO request) {
        return ResponseBaseDTO.ok(categoriesService.save(request));
    }

    @PutMapping("{id}")
    public ResponseBaseDTO updateCategories(
         @Valid @RequestBody RequestCategoriesDTO request, @PathVariable("id") Integer id
    ) {
       categoriesService.update(id, request);
       return ResponseBaseDTO.ok(categoriesService.update(id, request));
    }

    @GetMapping("/{id}")
    public ResponseBaseDTO<ResponseCategoriesDTO> getOne(@PathVariable Integer id) {
        return ResponseBaseDTO.ok(categoriesService.findById(id));
    }

    @DeleteMapping
    public ResponseBaseDTO deleteCategories(@RequestBody Categories categories) {
        
       return ResponseBaseDTO.ok(categoriesService.deleteById(categories.getId()));
    }

}