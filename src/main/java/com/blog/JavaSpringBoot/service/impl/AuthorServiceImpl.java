package com.blog.JavaSpringBoot.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import com.blog.JavaSpringBoot.config.ResourceServerConfig;
import com.blog.JavaSpringBoot.dto.request.RequestAuthorDTO;
import com.blog.JavaSpringBoot.dto.request.RequestAuthorPasswordDTO;
import com.blog.JavaSpringBoot.dto.request.RequestAuthorUpdateDTO;
import com.blog.JavaSpringBoot.dto.response.ResponseAuthorDTO;
import com.blog.JavaSpringBoot.dto.response.ResponseAuthorPasswordDTO;
import com.blog.JavaSpringBoot.dto.response.ResponseAuthorUpdateDTO;
import com.blog.JavaSpringBoot.dto.response.ResponseDataDTO;
import com.blog.JavaSpringBoot.dto.response.ResponseRolesDTO;
import com.blog.JavaSpringBoot.exception.ResourceNotFoundException;

import com.blog.JavaSpringBoot.repository.AuthorRepository;
import com.blog.JavaSpringBoot.repository.RolesRepository;
import com.blog.JavaSpringBoot.model.Author;
import com.blog.JavaSpringBoot.model.Roles;
import com.blog.JavaSpringBoot.service.Authorservice;
import com.blog.JavaSpringBoot.util.DateTime;
import org.springframework.core.env.Environment;
import com.blog.JavaSpringBoot.util.PropertiesUtil;
// import org.apache.logging.log4j.util.PropertiesUtil;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import org.springframework.stereotype.Service;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;

import org.springframework.context.annotation.Bean;


/**
 * TagServiceImpl
 */
@Slf4j
@Service
@PropertySource(value = "classpath:application.properties")
public class AuthorServiceImpl implements Authorservice {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private Environment env;

    @Autowired
    private PasswordEncoder encoder;
    
    @Autowired
    private ClientDetailsService clientDetailsStore;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private DataSource dataSource;

  


    @Autowired
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }
    
    @Autowired
    public AuthorizationServerTokenServices tokenServices() {
        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setAccessTokenValiditySeconds(-1);

        defaultTokenServices.setTokenStore(tokenStore());
        return defaultTokenServices;
    }
    
    private ResponseRolesDTO generateResponseRole(Roles obj) {
        ResponseRolesDTO res = new ResponseRolesDTO();

        try {
            res.setId(obj.getId());
            res.setName(obj.getName());
            res.setDescription(obj.getDescription());
          
            res.setCreated_at(obj.getCreated_at());
            res.setUpdated_at(obj.getUpdated_at());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }

    // @Bean
    // public BCryptPasswordEncoder passwordEncoder() {
    //     return new BCryptPasswordEncoder();
    // }

    @Autowired
    private DateTime dateTime;

    private static final String RESOURCE = "Author";
    private static final String FIELD = "id";
    
    @Override
    public ResponseEntity deleteById(Integer id) {
        ResponseDataDTO<ResponseAuthorDTO> responseData = new ResponseDataDTO<>();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Author roleLogin = (Author) auth.getPrincipal();      
        System.out.println("masukkkkk" + roleLogin.getRole().getId());

        
        try {
            Author author = authorRepository.getById(id);

            if (!roleLogin.getRole().getId().equals(PropertiesUtil.getRoleSysAdminID(env))) {            
                responseData = new ResponseDataDTO<ResponseAuthorDTO>(false, 404, "Access denied", null);
                return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
            } 
            if(author == null ){
                responseData = new ResponseDataDTO<ResponseAuthorDTO>(false, 404, "data author tidak ditemukan", null);
                return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);             
            }
            ResponseAuthorDTO response = new ResponseAuthorDTO() ;
            response.setId(author.getId());
            response.setFirst_name(author.getFirst_name());
            response.setLast_name(author.getLast_name());
            response.setUsername(author.getUsername());
            response.setPassword(author.getPassword());
            response.setCreated_at(author.getCreated_at());
            response.setUpdated_at(author.getUpdated_at());
            if (author.getRole() != null) {
                response.setRole(generateResponseRole(author.getRole()));
            }
         
            authorRepository.deleteById(id);
            responseData = new ResponseDataDTO<ResponseAuthorDTO>(true, 200, "success", response);
            return ResponseEntity.ok(responseData);
           

        } catch (Exception e) {
            responseData = new ResponseDataDTO<ResponseAuthorDTO>(false, 404, "delete data gagal", null);
            return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
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
    public ResponseEntity save(RequestAuthorDTO request) {
        ResponseDataDTO<ResponseAuthorDTO> responseData = new ResponseDataDTO<>();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Author roleLogin = (Author) auth.getPrincipal();      
        // Author roleLogin = authorRepository.findByUsername(auth.getName());
        System.out.println("masukkkkk" + roleLogin.getRole().getId());

        
        try {
            Author author = new Author();
            Roles roles =  rolesRepository.findByIdRoles(request.getRoles_id());

          
            if (!roleLogin.getRole().getId().equals(PropertiesUtil.getRoleSysAdminID(env))) {
            
                responseData = new ResponseDataDTO<ResponseAuthorDTO>(false, 404, "Access denied", null);
                return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
            } 
            if( roles == null ){
                // System.out.println("roles" + roleLogin.getRoles_id());

                responseData = new ResponseDataDTO<ResponseAuthorDTO>(false, 404, "role id tidak ditemukan", null);
                return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
            }    
            // System.out.println("save data" + roleLogin. roleLogin.getRole().getId());

            author.setRole(roles);
            author.setFirst_name(request.getFirst_name());
            author.setLast_name(request.getLast_name());
            author.setUsername(request.getUsername());
            author.setPassword(encoder.encode(request.getPassword()));
            author.setCreated_at(new Date());
            author.setUpdated_at(new Date());
            authorRepository.save(author);

            ResponseAuthorDTO response = new ResponseAuthorDTO() ;
            response.setId(author.getId());
            response.setFirst_name(author.getFirst_name());
            response.setLast_name(author.getLast_name());
            response.setUsername(author.getUsername());
            response.setPassword(author.getPassword());
            response.setCreated_at(author.getCreated_at());
            response.setUpdated_at(author.getUpdated_at());
            if (author.getRole() != null) {
                response.setRole(generateResponseRole(author.getRole()));
            }
         

            responseData = new ResponseDataDTO<ResponseAuthorDTO>(true, 200, "success", response);
            return ResponseEntity.ok(responseData);

        } catch (Exception e) {
            responseData = new ResponseDataDTO<ResponseAuthorDTO>(false, 404, "save data gagal", null);
            return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity update(Integer id, RequestAuthorUpdateDTO request) {
        ResponseDataDTO<ResponseAuthorDTO> responseData = new ResponseDataDTO<>();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Author roleLogin = (Author) auth.getPrincipal();      
        System.out.println("masukkkkk" + roleLogin.getRole().getId());

        
        try {
            Author author = authorRepository.getById(id);

          
            if (!roleLogin.getRole().getId().equals(PropertiesUtil.getRoleSysAdminID(env))) {            
                responseData = new ResponseDataDTO<ResponseAuthorDTO>(false, 404, "Access denied", null);
                return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
            } 
            if(author == null ){
                responseData = new ResponseDataDTO<ResponseAuthorDTO>(false, 404, "data author tidak ditemukan", null);
                return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);             
            }
       
            author.setFirst_name(request.getFirst_name());
            author.setLast_name(request.getLast_name());
            author.setUsername(request.getUsername());
            author.setUpdated_at(new Date());
            authorRepository.save(author);

            ResponseAuthorDTO response = new ResponseAuthorDTO() ;
            response.setId(author.getId());
            response.setFirst_name(author.getFirst_name());
            response.setLast_name(author.getLast_name());
            response.setUsername(author.getUsername());
            response.setPassword(author.getPassword());
            response.setCreated_at(author.getCreated_at());
            response.setUpdated_at(author.getUpdated_at());
            if (author.getRole() != null) {
                response.setRole(generateResponseRole(author.getRole()));
            }

            responseData = new ResponseDataDTO<ResponseAuthorDTO>(true, 200, "success", response);
            return ResponseEntity.ok(responseData);

        } catch (Exception e) {
            responseData = new ResponseDataDTO<ResponseAuthorDTO>(false, 404, "update data gagal", null);
            return new ResponseEntity<>(responseData, HttpStatus.NOT_FOUND);
        }
    }
            
    

    @Override
    public ResponseAuthorPasswordDTO updatePass(Integer id, RequestAuthorPasswordDTO request) {

        try {
            Author authors = authorRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(id.toString(), FIELD, RESOURCE));

            BeanUtils.copyProperties(request, authors);
            authors.setPassword(encoder.encode(request.getPassword()));
            authors.setUpdated_at(new Date());
            authorRepository.save(authors);

            return fromEntityPass(authors);
        } catch (ResourceNotFoundException e) {
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



    @Override
    public OAuth2AccessToken getToken(HashMap<String, String> params) throws HttpRequestMethodNotSupportedException {
        System.out.println("service getToken : " + params);
        if (params.get("username") == null) {
            throw new UsernameNotFoundException("username not found");
        }

        if (params.get("password") == null) {
            throw new UsernameNotFoundException("password not found");
        }

        params.put("client_id", ResourceServerConfig.CLIENT_ID);
        params.put("client_secret", ResourceServerConfig.CLIENT_SECRET);
        params.put("grant_type", "password");
        DefaultOAuth2RequestFactory defaultOAuth2RequestFactory = new DefaultOAuth2RequestFactory(clientDetailsStore);
        AuthorizationRequest authorizationRequest = defaultOAuth2RequestFactory.createAuthorizationRequest(params);
        authorizationRequest.setApproved(true);

        OAuth2Request oauth2Request = defaultOAuth2RequestFactory.createOAuth2Request(authorizationRequest);
        UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(params.get("username"), params.get("password"));
        Authentication authentication = authenticationManager.authenticate(loginToken);

        OAuth2Authentication authenticationRequest = new OAuth2Authentication(oauth2Request, authentication);
        authenticationRequest.setAuthenticated(true);

        OAuth2AccessToken token = tokenServices().createAccessToken(authenticationRequest);

        Map<String, Object> adInfo = new HashMap<>();
        adInfo.put("role", null);
        // Author author = (Author) authentication.getPrincipal();
        //     System.out.println("role  author: " + author);
            // adInfo.put("role", user.getRole().getName());
        try {
            Author user = (Author) authentication.getPrincipal();
            System.out.println("roles masukk : " + user.getRole().getName());
            adInfo.put("role", user.getRole().getName());
        } catch (Exception e) {
             e.printStackTrace();
        }

        ((DefaultOAuth2AccessToken) token).setAdditionalInformation(adInfo);

        return token;
    }

    @Override
    public OAuth2AccessToken getToken(Author author) throws HttpRequestMethodNotSupportedException {
        System.out.println("service : " + author);
        HashMap<String, String> params = new HashMap<String, String>();

        params.put("client_id", ResourceServerConfig.CLIENT_ID);
        params.put("client_secret", ResourceServerConfig.CLIENT_SECRET);
        params.put("grant_type", "password");
        params.put("username", author.getUsername());
        params.put("password", author.getPassword());

        DefaultOAuth2RequestFactory defaultOAuth2RequestFactory = new DefaultOAuth2RequestFactory(clientDetailsStore);
        AuthorizationRequest authorizationRequest = defaultOAuth2RequestFactory.createAuthorizationRequest(params);
        authorizationRequest.setApproved(true);

        OAuth2Request oauth2Request = defaultOAuth2RequestFactory.createOAuth2Request(authorizationRequest);
        UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(author, null, null); // user.getAuthorities()

        OAuth2Authentication authenticationRequest = new OAuth2Authentication(oauth2Request, loginToken);
        authenticationRequest.setAuthenticated(true);

        OAuth2AccessToken token = tokenServices().createAccessToken(authenticationRequest);

        Map<String, Object> adInfo = new HashMap<>();

        adInfo.put("role", null);

        try {
            adInfo.put("role", author.getRole().getName());
            System.out.println("role : " + author.getRole().getName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        ((DefaultOAuth2AccessToken) token).setAdditionalInformation(adInfo);

        return token;
    }

}