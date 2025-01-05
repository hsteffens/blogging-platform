package com.blogging.platform.repositories;

import com.blogging.platform.dto.CommentsDTO;
import com.blogging.platform.entity.BlogPost;
import com.blogging.platform.entity.Comments;
import com.blogging.platform.exceptions.ReadFromDBException;
import com.blogging.platform.exceptions.RecordCreationException;
import com.blogging.platform.exceptions.RecordUpdateException;
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
public class CommentsRepository {

    public static String COMMENTS_DB_FILE_NAME = "./comments.txt";

    public Comments createComments(final UUID blogPostID, final CommentsDTO commentsDTO) {
        Comments comments = new Comments(blogPostID, commentsDTO.getValues());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(COMMENTS_DB_FILE_NAME, true))) {
            ObjectMapper mapper = new ObjectMapper();
            writer.write(mapper.writeValueAsString(comments));
            writer.newLine();
        } catch (IOException e) {
            throw new RecordCreationException(e.toString());
        }

        return comments;
    }

    public void updateCommentByBlogPostId(final UUID blogPostID, final CommentsDTO commentsDTO) {
        List<Comments> comments = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(COMMENTS_DB_FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                ObjectMapper mapper = new ObjectMapper();
                Comments commentary = mapper.readValue(line, Comments.class);
                if (commentary.getBlogPostId().equals(blogPostID)) {
                    Comments newVersion = new Comments(blogPostID, commentsDTO.getValues());
                    commentary.getComments().addAll(newVersion.getComments());
                }

                comments.add(commentary);
            }

            writeAll(comments);
        } catch (IOException e) {
            throw new RecordUpdateException(e.toString());
        }
    }

    public Comments readByBlogPostId(final UUID blogPostID) {
        try (BufferedReader reader = new BufferedReader(new FileReader(COMMENTS_DB_FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                ObjectMapper mapper = new ObjectMapper();
                Comments comments = mapper.readValue(line, Comments.class);
                if (comments.getBlogPostId().equals(blogPostID)) {
                    return comments;
                }
            }
        } catch (IOException e) {
            throw new ReadFromDBException(e.toString());
        }
        return null;
    }

    private void writeAll(final List<Comments> comments) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(COMMENTS_DB_FILE_NAME))) {
            for (Comments commentary: comments) {
                ObjectMapper mapper = new ObjectMapper();
                writer.write(mapper.writeValueAsString(commentary));
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RecordCreationException(e.toString());
        }
    }
}
