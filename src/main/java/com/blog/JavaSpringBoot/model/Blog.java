package com.blog.JavaSpringBoot.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.blog.JavaSpringBoot.model.Comment;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Blog
 */
@Entity(name = "Blog")
@Table(name = "blog")
@Getter
@Setter
@ToString
public class Blog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;

    private transient Integer author_id;

    private transient Integer categories_id;

    // private transient List<Integer> tags_id;
    private transient List<String> tags_name;

    @Column(length = 150, nullable = false)
    @Size(min = 3, max = 150)
    @NotBlank
    private String title;

    @Column(length = 1000, nullable = false, columnDefinition = "TEXT")
    @Size(min = 10, max = 1000)
    @NotBlank
    private String content;

    // @Lob
    // @Column(name="image")
    // private byte[] image;

    // @Column(name="image_url")
    // private String imageUrl;

    @Column(updatable = false)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm:ss",timezone="GMT+7")
    @CreationTimestamp
    private Date created_at;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm:ss",timezone="GMT+7")
    @UpdateTimestamp
    private Date updated_at;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne
    @JoinColumn(name = "categories_id")
    private Categories categories;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
        name = "blog_tags", 
        joinColumns = { @JoinColumn(name = "blog_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "tags_id") }
    )    
    private List<Tags> tag = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,
    fetch = FetchType.LAZY,
    mappedBy = "blog")
    private Set<Comment> comment;

}