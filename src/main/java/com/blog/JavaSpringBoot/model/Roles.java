package com.blog.JavaSpringBoot.model;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Role
 */
@Entity
@Table(name = "roles")
@Getter
@Setter
@ToString

public class Roles implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;

    @Column(length = 20, nullable = false, unique = true, updatable = true)
    @Size(min = 2, max = 20)
    @NotBlank
    private String name;

    @Column(length = 20, nullable = false, unique = true, updatable = true)
    @Size(min = 2, max = 50)
    @NotBlank
    private String description;

    @Column(updatable = false)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm:ss",timezone="GMT+7")
    @CreationTimestamp
    private Date created_at;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm:ss",timezone="GMT+7")
    @UpdateTimestamp
    private Date updated_at;

}