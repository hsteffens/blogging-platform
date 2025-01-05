package com.blogging.platform.repositories;


import com.blogging.platform.dto.BlogPostDTO;
import com.blogging.platform.entity.BlogPost;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.UUID;

class BlogPostRepositoryTest {

    @AfterEach
    @BeforeEach
    void setup() {
        File file = new File(BlogPostRepository.BLOG_POSTS_DB_FILE_NAME);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void testCreateBlogPostWithSuccess() {
        final BlogPostDTO postDTO = BlogPostDTO.builder().title("Test").content("Testing").build();

        BlogPostRepository repository = new BlogPostRepository();
        final BlogPost post = repository.createBlogPost(postDTO);

        Assertions.assertNotNull(post.getId());
        Assertions.assertEquals(postDTO.getTitle(), post.getTitle());
        Assertions.assertEquals(postDTO.getContent(), post.getContent());
    }


    @Test
    void testReadByIdBlogPostWithSuccess() {
        final BlogPostDTO postDTO = BlogPostDTO.builder().title("Test").content("Testing").build();

        BlogPostRepository repository = new BlogPostRepository();
        final BlogPost created = repository.createBlogPost(postDTO);

        final BlogPost post = repository.readById(created.getId());

        Assertions.assertNotNull(post.getId());
        Assertions.assertEquals(postDTO.getTitle(), post.getTitle());
        Assertions.assertEquals(postDTO.getContent(), post.getContent());
    }

    @Test
    void testReadByIdBlogPostWhereIdNotFound() {
        final BlogPostDTO postDTO = BlogPostDTO.builder().title("Test").content("Testing").build();

        BlogPostRepository repository = new BlogPostRepository();
        repository.createBlogPost(postDTO);

        final BlogPost post = repository.readById(UUID.randomUUID());

        Assertions.assertNull(post);
    }

    @Test
    void testReadAllBlogPostWithSuccess() {
        BlogPostRepository repository = new BlogPostRepository();

        final BlogPostDTO postDTO = BlogPostDTO.builder().title("Test").content("Testing").build();
        final BlogPostDTO postDTO2 = BlogPostDTO.builder().title("Test 2").content("Testing 2").build();
        final BlogPostDTO postDTO3 = BlogPostDTO.builder().title("Test 3").content("Testing 3").build();

        repository.createBlogPost(postDTO);
        repository.createBlogPost(postDTO2);
        repository.createBlogPost(postDTO3);

        final List<BlogPost> posts = repository.readAll();

        Assertions.assertEquals(3, posts.size());
        Assertions.assertEquals(postDTO.getTitle(), posts.get(0).getTitle());
        Assertions.assertEquals(postDTO.getContent(), posts.get(0).getContent());

        Assertions.assertEquals(postDTO2.getTitle(), posts.get(1).getTitle());
        Assertions.assertEquals(postDTO2.getContent(), posts.get(1).getContent());

        Assertions.assertEquals(postDTO3.getTitle(), posts.get(2).getTitle());
        Assertions.assertEquals(postDTO3.getContent(), posts.get(2).getContent());
    }
}
