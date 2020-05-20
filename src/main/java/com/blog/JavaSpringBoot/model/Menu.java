package com.blog.JavaSpringBoot.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

@ToString
@Getter
@Setter
@Entity
@Table(name = "menu", schema = "public")
public class Menu implements Serializable{

    @Id
    @Column(name = "menu_id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("menu_id")
    private Integer menuId;

    @Column(name = "menu_parent_id")
    @JsonProperty("menu_parent_id")
    private Integer menuParentId;

    @Column(name = "menu_link", length = 200)
    @JsonProperty("menu_link")
    private String menuLink;

    @Column(name = "menu_hint_en", length = 100)
    @JsonProperty("menu_hint_en")
    private String menuHintEn;

    @Column(name = "menu_hint_id", length = 100)
    @JsonProperty("menu_hint_id")
    private String menuHintId;

    @Column(name = "menu_icon", length = 100)
    @JsonProperty("menu_icon")
    private String menuIcon;

    @Column(name = "menu_label_en", length = 100)
    @JsonProperty("menu_label_en")
    private String menuLabelEn;

    @Column(name = "menu_label_id", length = 100)
    @JsonProperty("menu_label_id")
    private String menuLabelId;

    @Column(name = "menu_type", length = 1)
    @JsonProperty("menu_type")
    private Integer menuType;

    @Column(updatable = false)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm:ss",timezone="GMT+7")
    @CreationTimestamp
    private Date created_at;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm:ss",timezone="GMT+7")
    @UpdateTimestamp
    private Date updated_at;

    // @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    // private Set<UserRoleMenu> roleMenus;

 
}