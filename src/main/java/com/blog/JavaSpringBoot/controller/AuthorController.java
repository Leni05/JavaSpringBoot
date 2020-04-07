package com.blog.JavaSpringBoot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javassist.NotFoundException;
import com.blog.JavaSpringBoot.model.Author;
import com.blog.JavaSpringBoot.repository.AuthorRepository;
import com.blog.JavaSpringBoot.service.Authorservice;

import javax.servlet.http.HttpServletRequest;

import com.blog.JavaSpringBoot.common.MyPage;
import com.blog.JavaSpringBoot.common.MyPageable;
import com.blog.JavaSpringBoot.common.ResponseDto.ResponseBaseDTO;
import com.blog.JavaSpringBoot.common.util.PageConverter;
import com.blog.JavaSpringBoot.exeption.ResponseBase;
import com.blog.JavaSpringBoot.model.request.AuthorDto;
import com.blog.JavaSpringBoot.model.request.AuthorPasswordDto;


/**
 * AuthorController
 */
@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private Authorservice authorService;

    @GetMapping("getAll")
    public ResponseEntity<ResponseBase> getAuthor() {
        ResponseBase response = new ResponseBase<>();

        response.setData(authorRepository.findAll());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ResponseBase> getAuthorById(@PathVariable Integer id) throws NotFoundException {
        ResponseBase response = new ResponseBase<>();

        Author author = authorRepository.findById(id).orElseThrow(() -> new NotFoundException("Author id " + id + " NotFound"));

        // author.setPassword(passwordEncoder().encode(author.getPassword()));
        response.setData(author);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ResponseBase> postAuthors(@RequestBody Author author) {
        ResponseBase response = new ResponseBase();

        try {
            
            response.setData(authorService.save(author));

            return new ResponseEntity<>(response ,HttpStatus.OK);

        } catch (Exception e) {

            response.setStatus(false);
            response.setCode(500);
            response.setMessage(e.getMessage() + "opppppp");

            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);

        }

    }


    @PutMapping("/{id}")
    public ResponseEntity<ResponseBase> putAuthor(@PathVariable Integer id, @RequestBody AuthorDto authorDto) throws NotFoundException {
        ResponseBase response = new ResponseBase<>();

        Author author = authorRepository.findById(id).orElseThrow(() -> new NotFoundException("Author id " + id + " NotFound"));

        try {
            author.setFirst_name(authorDto.getFirst_name());
            author.setLast_name(authorDto.getLast_name());
            author.setUsername(authorDto.getUsername());

            response.setData(authorService.update(id, author));
        } catch (Exception e) {
            response.setStatus(false);
            response.setCode(500);
            response.setMessage(e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBase> deleteAuthor(@PathVariable Integer id) {
        ResponseBase response = new ResponseBase<>();

        try {
            authorRepository.deleteById(id);
        } catch (Exception e) {
            response.setStatus(false);
            response.setCode(500);
            response.setMessage(e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    

    @PutMapping("/{id}/password")
    public ResponseEntity<ResponseBase> putPaswordAuthor(@PathVariable Integer id, @RequestBody AuthorPasswordDto authorPasswordDto) throws NotFoundException {
        ResponseBase response = new ResponseBase<>();

        Author author = authorRepository.findById(id).orElseThrow(() -> new NotFoundException("Author id " + id + " NotFound"));

        try {
            author.setPassword(authorPasswordDto.getPassword());

            response.setData(authorService.changePassword(id, author));
        } catch (Exception e) {
            response.setStatus(false);
            response.setCode(500);
            response.setMessage(e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<ResponseBase> deleteAuthorById(@RequestBody Author author) throws NotFoundException {
        ResponseBase response = new ResponseBase<>();

        Author authors = authorRepository.findById(author.getId()).orElseThrow(() -> new NotFoundException("Author id " + author.getId() + " NotFound"));

        try {
            authorRepository.deleteById(authors.getId());
        } catch (Exception e) {
            response.setStatus(false);
            response.setCode(500);
            response.setMessage(e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //=================================================== With pagination ================================================

    @GetMapping
    public ResponseBaseDTO<MyPage<Author>> getALl(
        MyPageable pageable, @RequestParam(required = false) String param, HttpServletRequest request
    ) { 
        try {
        Page<Author> authors;

        if (param != null) {
            authors = authorService.findAuthor(MyPageable.convertToPageable(pageable), param);
        } else {
            authors = authorService.findAll(MyPageable.convertToPageable(pageable));
        }

        PageConverter<Author> converter = new PageConverter<>();
        String url = String.format("%s://%s:%d/tags",request.getScheme(),  request.getServerName(), request.getServerPort());

        String search = "";

        if(param != null){
            search += "&param="+param;
        }

        MyPage<Author> respon = converter.convert(authors, url, search);

        return ResponseBaseDTO.ok(respon);
        
        } catch (Exception e) {

            return ResponseBaseDTO.error("200", e.getMessage());
        
        }

    }

}