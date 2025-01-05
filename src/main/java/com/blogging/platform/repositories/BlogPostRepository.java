package com.blogging.platform.repositories;

import com.blogging.platform.dto.BlogPostDTO;
import com.blogging.platform.entity.BlogPost;
import com.blogging.platform.entity.Comments;
import com.blogging.platform.exceptions.ReadFromDBException;
import com.blogging.platform.exceptions.RecordCreationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BlogPostRepository {

    public static String BLOG_POSTS_DB_FILE_NAME = "./blog_posts.txt";

    public BlogPost createBlogPost(final BlogPostDTO postDTO) {
        BlogPost post = new BlogPost(postDTO.getTitle(), postDTO.getContent());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BLOG_POSTS_DB_FILE_NAME, true))) {
            ObjectMapper mapper = new ObjectMapper();
            writer.write(mapper.writeValueAsString(post));
            writer.newLine();
        } catch (IOException e) {
            throw new RecordCreationException(e.toString());
        }
        return post;
    }

    public List<BlogPost> readAll() {
        List<BlogPost> posts = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(BLOG_POSTS_DB_FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                ObjectMapper mapper = new ObjectMapper();
                BlogPost post = mapper.readValue(line, BlogPost.class);
                posts.add(post);
            }
        } catch (IOException e) {
            throw new ReadFromDBException(e.toString());
        }
        return posts;
    }

    public BlogPost readById(final UUID id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(BLOG_POSTS_DB_FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                ObjectMapper mapper = new ObjectMapper();
                BlogPost post = mapper.readValue(line, BlogPost.class);
                if (post.getId().equals(id)) {
                    return post;
                }
            }
        } catch (IOException e) {
            throw new ReadFromDBException(e.toString());
        }
        return null;
    }
}
