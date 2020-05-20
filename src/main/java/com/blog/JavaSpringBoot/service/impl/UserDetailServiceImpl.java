package com.blog.JavaSpringBoot.service.impl;

import com.blog.JavaSpringBoot.model.Author;
import com.blog.JavaSpringBoot.service.Authorservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService{
    
    @Autowired
    private Authorservice authorservice;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Author author = this.authorservice.getByUsername(username);

        if (author != null) {
            return author;
        }

        throw new UsernameNotFoundException("User not found");
    }

}
