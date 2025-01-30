package com.debangan.blogs.repository;

import com.debangan.blogs.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepo extends JpaRepository<Blog,Integer> {

}
