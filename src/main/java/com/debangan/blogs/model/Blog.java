package com.debangan.blogs.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "blogs")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    @Column(nullable = false)
    String title;
    @Column(nullable = false)
    String description;

}

