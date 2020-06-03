package com.blog.JavaSpringBoot.repository;


import com.blog.JavaSpringBoot.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import antlr.collections.List;

/**
 * RolessRepository
 */
@Transactional(readOnly = true)
public interface RolesRepository extends PagingAndSortingRepository<Roles, Integer> {
    Roles findByName(String name);
   
   	@Query("select e from #{#entityName} e where e.name like %:param% ")
    Page<Roles> findByName(Pageable pageable, String param);
    
    @Query(value = "SELECT * from roles WHERE id = ?1", nativeQuery = true)
    Roles findByIdRoles(Integer id);

    @Query(value = "SELECT * from roles WHERE name =:roles", nativeQuery = true)
    Roles findByNames(String roles);
    
}