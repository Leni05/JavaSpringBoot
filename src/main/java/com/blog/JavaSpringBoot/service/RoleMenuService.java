package com.blog.JavaSpringBoot.service;


public interface RoleMenuService {
    boolean roleAccess (String url, String method);
}