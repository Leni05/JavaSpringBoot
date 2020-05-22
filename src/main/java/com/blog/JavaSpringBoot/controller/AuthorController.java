package com.blog.JavaSpringBoot.controller;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.HttpRequestMethodNotSupportedException;
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

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import com.blog.JavaSpringBoot.model.Author;
import com.blog.JavaSpringBoot.model.Roles;
import com.blog.JavaSpringBoot.repository.AuthorRepository;
import com.blog.JavaSpringBoot.repository.RolesRepository;
import com.blog.JavaSpringBoot.service.Authorservice;
import com.blog.JavaSpringBoot.exception.ResponseBase;
import com.blog.JavaSpringBoot.dto.response.ResponseBaseDTO;
import com.blog.JavaSpringBoot.dto.response.ResponseOauthDTO;
import com.blog.JavaSpringBoot.dto.response.ResponseAuthorDTO;
import com.blog.JavaSpringBoot.dto.response.ResponseAuthorPasswordDTO;
import com.blog.JavaSpringBoot.dto.response.ResponseAuthorPasswordDTO;
import com.blog.JavaSpringBoot.dto.response.ResponseAuthorUpdateDTO;
import com.blog.JavaSpringBoot.dto.request.LoginRequest;
import com.blog.JavaSpringBoot.dto.request.RequestAuthorDTO;
import com.blog.JavaSpringBoot.dto.request.RequestAuthorPasswordDTO;
import com.blog.JavaSpringBoot.dto.request.RequestAuthorUpdateDTO;
import com.blog.JavaSpringBoot.util.PageConverter;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.blog.JavaSpringBoot.config.MyPage;
import com.blog.JavaSpringBoot.config.MyPageable;


/**
 * AuthorController
 */
@RestController
public class AuthorController {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private Authorservice authorService;


    //=================================================== With pagination ================================================

    @GetMapping("/authors")
    public ResponseBaseDTO<MyPage<ResponseAuthorDTO>> listAuthor(
        MyPageable pageable, @RequestParam(required = false) String param, HttpServletRequest request
    ) { 
       Page<ResponseAuthorDTO> authors;

        if (param != null) {
            authors = authorService.findByName(MyPageable.convertToPageable(pageable), param);
        } else {
            authors = authorService.findAll(MyPageable.convertToPageable(pageable));
        }

        PageConverter<ResponseAuthorDTO> converter = new PageConverter<>();
        String url = String.format("%s://%s:%d/authors",request.getScheme(),  request.getServerName(), request.getServerPort());

        String search = "";

        if(param != null){
            search += "&param="+param;
        }

        MyPage<ResponseAuthorDTO> response = converter.convert(authors, url, search);

        return ResponseBaseDTO.ok(response);

    }

    // @PostMapping("/authors")
    // public ResponseBaseDTO createAuthor(@Valid @RequestBody RequestAuthorDTO request) {
    //     return ResponseBaseDTO.ok(authorService.save(request));
    // }
    @PostMapping("/authors")
    public ResponseEntity createAuthor(@Valid @RequestBody RequestAuthorDTO request) {
        return authorService.save(request);
    }

    @GetMapping("/authors/{id}")
    public ResponseBaseDTO<ResponseAuthorDTO> getOne(@PathVariable Integer id) {
        return ResponseBaseDTO.ok(authorService.findById(id));
    }

    @PutMapping("/authors/{id}")
    public ResponseEntity updateAuthor(
         @Valid @RequestBody RequestAuthorUpdateDTO request, @PathVariable("id") Integer id
    ) {     
       return authorService.update(id, request);
    }

    @PutMapping("/authors/{id}/password")
    public ResponseBaseDTO updateAuthorPass(
         @Valid @RequestBody RequestAuthorPasswordDTO request, @PathVariable("id") Integer id
    ) {
       authorService.updatePass(id, request);
       return ResponseBaseDTO.ok(authorService.updatePass(id, request));
    }

    @DeleteMapping("/authors")
    public ResponseEntity delete(@RequestBody Author author) {        
       return authorService.deleteById(author.getId());
    }


    @PostMapping("/login")
    public ResponseBaseDTO<OAuth2AccessToken> login(@RequestBody LoginRequest request) {
     
        HashMap<String, String> params = request.getMap();
        Author checkUser = authorService.getByUsername(params.get("username"));

        
        if (checkUser == null) {
            return ResponseBaseDTO.error("404", "User Not Found");
        }

        // if (checkUser != null) {
        //     return ResponseBaseDTO.error("0001", "Your account is inactive, please contact support center");
        // }

        // if (checkUser != null && checkUser.getIsActive() == 2) {
        //     return BaseResponse.error("0004", "Your account has been suspended, please contact support center");
        // }

        try {
            System.out.println("masuuuukkkkkkk" + checkUser+ "params : " + params);
            OAuth2AccessToken token = this.authorService.getToken(params);
            // if (checkUser != null) {
            //     authorService.resetAttempt(checkUser.getId());
            // }

            return ResponseBaseDTO.ok(token);
        } catch (Exception e) {
            e.printStackTrace();
            // if (checkUser != null) {
            //     if (checkUser.getLoginAttempt() != null && checkUser.getLoginAttempt() == 5) {
            //         ResponseBaseDTO.suspendUserLogin(checkUser.getId());

            //         return ResponseBaseDTO.error("0004", "Your account has been suspended, please contact support center");
            //     } else {
            //         userService.updateAttempt(checkUser.getId());

            //         return BaseResponse.error("0005", "Your password is incorrect");
            //     }
            // }
            return ResponseBaseDTO.error("404", "Error Login");
        }

        // return ResponseBaseDTO.error("404", "Internal Server Error");
    }

}