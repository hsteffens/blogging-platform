package com.blogging.platform.controllers;

import com.blogging.platform.dto.BlogPostDTO;
import com.blogging.platform.dto.CommentsDTO;
import com.blogging.platform.services.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/posts")
public class BlogPostController {

    @Autowired
    private BlogPostService service;

    @GetMapping
    public List<BlogPostDTO> findAll() {
        return service.readAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BlogPostDTO create(@RequestBody BlogPostDTO post) {
        return service.createBlogPost(post);
    }

    @GetMapping("/{id}")
    public BlogPostDTO findById(@PathVariable("id") String id) {
        return service.readBlogPost(UUID.fromString(id));
    }

    @PostMapping("/{id}/comments")
    public BlogPostDTO addNewComment(@PathVariable("id") String id, @RequestBody CommentsDTO comments) {
        return service.updateBlogPostComments(UUID.fromString(id), comments);
    }

}
