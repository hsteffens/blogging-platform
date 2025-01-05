package com.blogging.platform.services;

import com.blogging.platform.dto.BlogPostDTO;
import com.blogging.platform.dto.CommentsDTO;
import com.blogging.platform.entity.BlogPost;
import com.blogging.platform.entity.Comments;
import com.blogging.platform.repositories.BlogPostRepository;
import com.blogging.platform.repositories.CommentsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

class BlogPostServiceTest {

    @InjectMocks
    private BlogPostService service;

    @Mock
    private BlogPostRepository blogPostRepository;

    @Mock
    private CommentsRepository commentsRepository;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBlogPostWithSuccess() {
        BlogPost post = new BlogPost("test", "testing");
        Comments comments = new Comments(post.getId(), List.of("hahaha"));
        Mockito.when(blogPostRepository.createBlogPost(Mockito.any())).thenReturn(post);
        Mockito.when(commentsRepository.createComments(Mockito.any(), Mockito.any())).thenReturn(null);

        Mockito.when(blogPostRepository.readById(post.getId())).thenReturn(post);
        Mockito.when(commentsRepository.readByBlogPostId(post.getId())).thenReturn(comments);

        final BlogPostDTO blogPost = service.createBlogPost(BlogPostDTO.builder()
                .title("test")
                .content("testing")
                .comments(CommentsDTO.builder().values(List.of("hahaha")).build())
                .build());

        Assertions.assertEquals(post.getId(), blogPost.getId());
        Assertions.assertEquals(post.getTitle(), blogPost.getTitle());
        Assertions.assertEquals(post.getContent(), blogPost.getContent());
        Assertions.assertEquals(comments.getComments(), blogPost.getComments().getValues());
    }

    @Test
    void testReadBlogPostByIdWithSuccess() {
        BlogPost post = new BlogPost("test", "testing");
        Comments comments = new Comments(post.getId(), List.of("hahaha"));

        Mockito.when(blogPostRepository.readById(post.getId())).thenReturn(post);
        Mockito.when(commentsRepository.readByBlogPostId(post.getId())).thenReturn(comments);

        final BlogPostDTO blogPost = service.readBlogPost(post.getId());

        Assertions.assertEquals(post.getId(), blogPost.getId());
        Assertions.assertEquals(post.getTitle(), blogPost.getTitle());
        Assertions.assertEquals(post.getContent(), blogPost.getContent());
        Assertions.assertEquals(comments.getComments(), blogPost.getComments().getValues());
    }

    @Test
    void testReadAllBlogPostWithSuccess() {
        BlogPost post = new BlogPost("test", "testing");
        BlogPost post2 = new BlogPost("test2", "testing 2");
        BlogPost post3 = new BlogPost("test 3", "testing 3");
        Comments comments = new Comments(post.getId(), List.of("hahaha"));
        Comments comments2 = new Comments(post.getId(), List.of("hahaha 2"));
        Comments comments3 = new Comments(post.getId(), List.of("hahaha 3"));


        Mockito.when(blogPostRepository.readAll()).thenReturn(List.of(post, post2, post3));
        Mockito.when(commentsRepository.readByBlogPostId(post.getId())).thenReturn(comments);
        Mockito.when(commentsRepository.readByBlogPostId(post2.getId())).thenReturn(comments2);
        Mockito.when(commentsRepository.readByBlogPostId(post3.getId())).thenReturn(comments3);


        final List<BlogPostDTO> posts = service.readAll();

        Assertions.assertEquals(3, posts.size());

        Assertions.assertEquals(post.getId(), posts.get(0).getId());
        Assertions.assertEquals(post.getTitle(), posts.get(0).getTitle());
        Assertions.assertEquals(post.getContent(), posts.get(0).getContent());
        Assertions.assertEquals(comments.getComments(), posts.get(0).getComments().getValues());

        Assertions.assertEquals(post2.getId(), posts.get(1).getId());
        Assertions.assertEquals(post2.getTitle(), posts.get(1).getTitle());
        Assertions.assertEquals(post2.getContent(), posts.get(1).getContent());
        Assertions.assertEquals(comments2.getComments(), posts.get(1).getComments().getValues());

        Assertions.assertEquals(post3.getId(), posts.get(2).getId());
        Assertions.assertEquals(post3.getTitle(), posts.get(2).getTitle());
        Assertions.assertEquals(post3.getContent(), posts.get(2).getContent());
        Assertions.assertEquals(comments3.getComments(), posts.get(2).getComments().getValues());
    }

    @Test
    void testUpdateBlogPostCommentsWithSuccess() {
        BlogPost post = new BlogPost("test", "testing");
        CommentsDTO commentsDTO = CommentsDTO.builder().values(List.of("hahaha")).build();
        Comments comments = new Comments(post.getId(), List.of("hahaha"));

        Mockito.when(blogPostRepository.readById(post.getId())).thenReturn(post);
        Mockito.when(commentsRepository.readByBlogPostId(post.getId())).thenReturn(comments);

        final BlogPostDTO blogPost = service.updateBlogPostComments(post.getId(), commentsDTO);

        Assertions.assertEquals(post.getId(), blogPost.getId());
        Assertions.assertEquals(post.getTitle(), blogPost.getTitle());
        Assertions.assertEquals(post.getContent(), blogPost.getContent());
        Assertions.assertEquals(comments.getComments(), blogPost.getComments().getValues());
    }
}
