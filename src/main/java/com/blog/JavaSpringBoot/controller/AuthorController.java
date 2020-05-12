package com.blog.JavaSpringBoot.controller;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
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
import com.blog.JavaSpringBoot.repository.AuthorRepository;
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

// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * AuthorController
 */
@RestController
public class AuthorController {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private Authorservice authorService;

    @Autowired
	private DataSource dataSource;

	@Autowired
    private ClientDetailsService clientDetailsStore;

    @Autowired
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
	}

    @Autowired
	private AuthenticationManager authenticationManager;


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

    @PostMapping("/authors")
    public ResponseBaseDTO createAuthor(@Valid @RequestBody RequestAuthorDTO request) {
        return ResponseBaseDTO.ok(authorService.save(request));
    }

    @GetMapping("/authors/{id}")
    public ResponseBaseDTO<ResponseAuthorDTO> getOne(@PathVariable Integer id) {
        return ResponseBaseDTO.ok(authorService.findById(id));
    }

    @PutMapping("/authors/{id}")
    public ResponseBaseDTO updateAuthor(
         @Valid @RequestBody RequestAuthorUpdateDTO request, @PathVariable("id") Integer id
    ) {
       authorService.update(id, request);
       return ResponseBaseDTO.ok(authorService.update(id, request));
    }

    @PutMapping("/authors/{id}/password")
    public ResponseBaseDTO updateAuthorPass(
         @Valid @RequestBody RequestAuthorPasswordDTO request, @PathVariable("id") Integer id
    ) {
       authorService.updatePass(id, request);
       return ResponseBaseDTO.ok(authorService.updatePass(id, request));
    }

    @DeleteMapping("/authors")
    public ResponseBaseDTO deleteTag(@RequestBody Author author) {
        
       return ResponseBaseDTO.ok(authorService.deleteById(author.getId()));
    }


    //Normal Login
    @RequestMapping(value="/api/login", method = RequestMethod.POST)

	public  ResponseEntity<ResponseOauthDTO> login(@RequestParam HashMap<String, String> params) throws Exception
	{
        // System.out.println("masukkkkk loginn");
		ResponseOauthDTO response = new ResponseOauthDTO();
		Author checkUser =  authorService.getByUsername(params.get("username"));

	    if (checkUser != null)
		{
            // System.out.println("masukkkkk sini");
			try {
				OAuth2AccessToken token = this.getToken(params);
			
				response.setStatus(true);
				response.setCode("200");
				response.setMessage("success");
				response.setData(token);

				return new ResponseEntity<>(response, HttpStatus.OK);
			} catch (Exception exception) {
				
                    response.setStatus(false);
                    response.setCode("500");
                    response.setMessage(exception.getMessage());
			}
		} else {
            // System.out.println("masukkkkk catch");
			throw new Exception();
		}
		

		return new ResponseEntity<ResponseOauthDTO>(response, HttpStatus.UNAUTHORIZED);
    }
    
    private OAuth2AccessToken getToken(HashMap<String, String> params) throws HttpRequestMethodNotSupportedException {
		if (params.get("username") == null ) {
			throw new UsernameNotFoundException("username not found");
		}

		if (params.get("password") == null) {
			throw new UsernameNotFoundException("password not found");
		}

		if (params.get("client_id") == null) {
			throw new UsernameNotFoundException("client_id not found");
		}

		if (params.get("client_secret") == null) {
			throw new UsernameNotFoundException("client_secret not found");
		}

		DefaultOAuth2RequestFactory defaultOAuth2RequestFactory = new DefaultOAuth2RequestFactory(clientDetailsStore);

		AuthorizationRequest authorizationRequest = defaultOAuth2RequestFactory.createAuthorizationRequest(params);
		authorizationRequest.setApproved(true);

		OAuth2Request oauth2Request = defaultOAuth2RequestFactory.createOAuth2Request(authorizationRequest);
		
		final UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(
				params.get("username"), params.get("password"));
		org.springframework.security.core.Authentication authentication = authenticationManager
                .authenticate(loginToken);

		OAuth2Authentication authenticationRequest = new OAuth2Authentication(oauth2Request, authentication);
		authenticationRequest.setAuthenticated(true);

		OAuth2AccessToken token = tokenServices().createAccessToken(authenticationRequest);


		return token;
    } 
    
    @Autowired
	public AuthorizationServerTokenServices tokenServices() {
		final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setAccessTokenValiditySeconds(-1);

		defaultTokenServices.setTokenStore(tokenStore());
		return defaultTokenServices;
	}

  
}