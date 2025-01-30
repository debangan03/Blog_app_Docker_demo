package com.debangan.blogs.service;


import com.debangan.blogs.model.Blog;
import com.debangan.blogs.repository.BlogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {

    @Autowired
    private BlogRepo blogRepo;

    public List<Blog> getAllBlogs(){
        return blogRepo.findAll();

    }

    public Blog addNewBlog(Blog blog){
        return blogRepo.save(blog);
    }

    public void deleteEntry(int id){
        blogRepo.deleteById(id);
    }
}
