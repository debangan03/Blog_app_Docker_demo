package com.debangan.blogs.controller;


import com.debangan.blogs.model.Blog;
import com.debangan.blogs.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/blog")
public class BlogController {

    private static final Logger log = LoggerFactory.getLogger(BlogController.class);




    private final BlogService blogService;

    @GetMapping
    public ResponseEntity<Object> getBlogs(){
        try{
            log.info("get blogs api");
            List<Blog> allBlogs = blogService.getAllBlogs();
            return new ResponseEntity<>(allBlogs, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error(String.valueOf(e));
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


    }

    @PostMapping
    public ResponseEntity<Object> postBlog(@RequestBody Blog blog){
        try{
            log.info("post blogs api");
            Blog newBlogs = blogService.addNewBlog(blog);
            return new ResponseEntity<>(newBlogs, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            log.error(String.valueOf(e));
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEntry(@PathVariable int id){
        log.info("delete blogs api");
        blogService.deleteEntry(id);

        return new ResponseEntity<>("successfully deleted",HttpStatus.OK);
    }
}
