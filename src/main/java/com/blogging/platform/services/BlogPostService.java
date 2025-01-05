package com.blogging.platform.services;

import com.blogging.platform.dto.BlogPostDTO;
import com.blogging.platform.dto.CommentsDTO;
import com.blogging.platform.entity.BlogPost;
import com.blogging.platform.entity.Comments;
import com.blogging.platform.repositories.BlogPostRepository;
import com.blogging.platform.repositories.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class BlogPostService {

    @Autowired
    private BlogPostRepository blogPostRepository;

    @Autowired
    private CommentsRepository commentsRepository;

    public BlogPostDTO createBlogPost(final BlogPostDTO postDTO) {
        final BlogPost blogPost = blogPostRepository.createBlogPost(postDTO);
        commentsRepository.createComments(blogPost.getId(), postDTO.getComments());

        final BlogPost blogPost1 = blogPostRepository.readById(blogPost.getId());
        final Comments comments = commentsRepository.readByBlogPostId(blogPost.getId());

        return BlogPostDTO.builder()
                .id(blogPost1.getId())
                .title(blogPost1.getTitle())
                .content(blogPost1.getContent())
                .comments(CommentsDTO.builder().values(comments.getComments()).build())
                .build();
    }

    public List<BlogPostDTO> readAll() {
        return blogPostRepository.readAll().stream()
                .map(post -> {
                    final Comments comments = commentsRepository.readByBlogPostId(post.getId());

                    return BlogPostDTO.builder()
                            .id(post.getId())
                            .title(post.getTitle())
                            .content(post.getContent())
                            .comments(CommentsDTO.builder().values(comments.getComments()).build())
                            .build();
                }).collect(Collectors.toList());
    }

    public BlogPostDTO readBlogPost(final UUID id) {
        final BlogPost blogPost = blogPostRepository.readById(id);
        final Comments comments = commentsRepository.readByBlogPostId(id);

        return BlogPostDTO.builder()
                .id(blogPost.getId())
                .title(blogPost.getTitle())
                .content(blogPost.getContent())
                .comments(CommentsDTO.builder().values(comments.getComments()).build())
                .build();
    }

    public BlogPostDTO updateBlogPostComments(final UUID id, final CommentsDTO commentsDTO) {
        final BlogPost blogPost = blogPostRepository.readById(id);
        commentsRepository.updateCommentByBlogPostId(id, commentsDTO);

        final Comments comments = commentsRepository.readByBlogPostId(id);

        return BlogPostDTO.builder()
                .id(blogPost.getId())
                .title(blogPost.getTitle())
                .content(blogPost.getContent())
                .comments(CommentsDTO.builder().values(comments.getComments()).build())
                .build();
    }
}
