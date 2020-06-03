package com.blog.JavaSpringBoot.repository;

import com.blog.JavaSpringBoot.model.Menu;
import com.blog.JavaSpringBoot.model.Roles;
import com.blog.JavaSpringBoot.model.UserRoleMenu;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

// import antlr.collections.List;

/**
 * TagsRepository
 */
// @Transactional(readOnly = true)
public interface UserRoleMenuRepository extends PagingAndSortingRepository<UserRoleMenu, Integer> {
   
    @Query("select e from #{#entityName} e where e.roles=:roles AND e.menu=:menu AND e.requestType=:method")
    public UserRoleMenu findByRoleIdAndMenuId(Roles roles, Menu menu, String method);  
}