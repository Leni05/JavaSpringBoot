package com.blog.JavaSpringBoot.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Author
 */
@Entity
@Table(name = "author")
@Getter
@Setter
@ToString
public class Author implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    private Integer id;

    @Column(length = 45, nullable = false)
    @Size(min = 3, max = 45)
    @NotBlank
    private String first_name;

    @Column(length = 45)
    @Size(min = 3, max = 45)
    private String last_name;

    @Column(length = 45, nullable = false, unique = true)
    @Size(min = 3, max = 45)
    @NotBlank
    private String username;

    @Column(length = 150, nullable = false)
    @NotBlank
    private String password;

    @Column(updatable = false)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+7")
    @CreationTimestamp
    private Date created_at;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+7")
    @UpdateTimestamp
    private Date updated_at;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Roles role;

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }


}