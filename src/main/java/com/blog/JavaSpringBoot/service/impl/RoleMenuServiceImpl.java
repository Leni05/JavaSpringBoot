package com.blog.JavaSpringBoot.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.blog.JavaSpringBoot.model.Author;
import com.blog.JavaSpringBoot.model.Menu;
import com.blog.JavaSpringBoot.model.Roles;
import com.blog.JavaSpringBoot.model.UserRoleMenu;
import com.blog.JavaSpringBoot.repository.AuthorRepository;
import com.blog.JavaSpringBoot.repository.MenuRepository;
import com.blog.JavaSpringBoot.repository.RolesRepository;
import com.blog.JavaSpringBoot.repository.UserRoleMenuRepository;
import com.blog.JavaSpringBoot.service.RoleMenuService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javassist.NotFoundException;

@Service
public class RoleMenuServiceImpl implements RoleMenuService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private UserRoleMenuRepository userRoleMenuRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Override
    public boolean roleAccess(String url, String method) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Author userAuth = (Author) auth.getPrincipal();

        Author author = authorRepository.getById(userAuth.getId());

        // findMenu
        Menu menu = menuRepository.findByMenu(url);

        // find role
        Roles roles = rolesRepository.findByNames(author.getRole().getName());

        UserRoleMenu roleAccess = userRoleMenuRepository.findByRoleIdAndMenuId(roles,menu, method);
        System.out.println("ini roleeesssssss : " + roles + "method : "+method + "rolesaccess : " + roleAccess)  ;
        if(roleAccess != null){
            return true;
        }
        return false;
    }
}