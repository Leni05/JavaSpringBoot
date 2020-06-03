package com.blog.JavaSpringBoot.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.Date;

@ToString
@Getter
@Setter
@Entity
@Table(name = "user_role_apps_menu", schema = "public")
public class UserRoleMenu implements Serializable {

    // @EmbeddedId
    // private UserRoleMenuKey id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("user_role_id")
    @JoinColumn(name = "user_role_id")
    private Roles roles;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("menu_id")
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @Column(name = "rm_read_write")
    private Integer readWrite;

    @Column(updatable = false)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm:ss",timezone="GMT+7")
    @CreationTimestamp
    private Date created_at;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm:ss",timezone="GMT+7")
    @UpdateTimestamp
    private Date updated_at;

    @Column(name="request_type")
	private String requestType;


}
