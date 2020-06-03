package com.blog.JavaSpringBoot.repository;

import javax.transaction.Transactional;

import com.blog.JavaSpringBoot.model.Menu;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {
    
    // @Transactional
    // @Modifying
    // @Query("UPDATE Menu set menuIsDelete = 1, menuDeleteTime = now() WHERE id = :id AND menuIsDelete = 0")
    // int deletePassword(@Param("id") Integer id);

    @Query(value = "SELECT * from menu WHERE id = ?1", nativeQuery = true)
    Menu getMenuById(Integer id);

    @Query("SELECT m FROM Menu m ")
    Page<Menu> findAllMenu(Pageable pageable);

    // @Query(value = "SELECT * from menu WHERE menuLink = ?1", nativeQuery = true)
    // Menu getMenuLink(String params);

    // @Query("select e from #{#entityName} e where e.menuLink=:params")
    // public getMenuLink findByRoleIdAndMenuId(String params);

    // @Query("select e from #{#entityName} e where e.menu_link like %:param% ")
    // Menu findByMenu(String param);

    @Query(value = "SELECT * from menu WHERE menu_link =:params", nativeQuery = true)
    Menu findByMenu(String params);



}

