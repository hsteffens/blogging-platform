package com.blogging.platform.repositories;

import com.blogging.platform.dto.CommentsDTO;
import com.blogging.platform.entity.Comments;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.UUID;


class CommentsRepositoryTest {

    @AfterEach
    @BeforeEach
    void setup() {
        File file = new File(CommentsRepository.COMMENTS_DB_FILE_NAME);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void testCreateCommentaryWithSuccess() {
        final UUID id = UUID.randomUUID();
        final List<String> commentaries = List.of("test", "test 2");
        final CommentsDTO commentsDTO = CommentsDTO.builder().values(commentaries).build();
        CommentsRepository repository = new CommentsRepository();
        final Comments comments = repository.createComments(id, commentsDTO);
        Assertions.assertNotNull(comments.getId());
        Assertions.assertEquals(id, comments.getBlogPostId());
        Assertions.assertEquals(commentaries, comments.getComments());
    }

    @Test
    void testReadByBlogPostIdWithSuccess() {
        final UUID id = UUID.randomUUID();
        final List<String> commentaries = List.of("test", "test 2");
        final CommentsDTO commentsDTO = CommentsDTO.builder().values(commentaries).build();
        CommentsRepository repository = new CommentsRepository();
        repository.createComments(id, commentsDTO);

        final Comments comments = repository.readByBlogPostId(id);

        Assertions.assertNotNull(comments.getId());
        Assertions.assertEquals(id, comments.getBlogPostId());
        Assertions.assertEquals(commentaries, comments.getComments());
    }

    @Test
    void testReadByBlogPostIdWhereIdNotFound() {
        final UUID id = UUID.randomUUID();
        final List<String> commentaries = List.of("test", "test 2");
        final CommentsDTO commentsDTO = CommentsDTO.builder().values(commentaries).build();
        CommentsRepository repository = new CommentsRepository();
        repository.createComments(id, commentsDTO);

        final Comments comments = repository.readByBlogPostId(UUID.randomUUID());

        Assertions.assertNull(comments);
    }

    @Test
    void testUpdateCommentByBlogPostId() {
        CommentsRepository repository = new CommentsRepository();

        final UUID id = UUID.randomUUID();
        final List<String> commentaries = List.of("test", "test 2");
        final CommentsDTO commentsDTO = CommentsDTO.builder().values(commentaries).build();

        repository.createComments(id, commentsDTO);


        final UUID id2 = UUID.randomUUID();
        final List<String> commentaries2 = List.of("test", "test 2");
        final CommentsDTO commentsDTO2 = CommentsDTO.builder().values(commentaries2).build();

        repository.createComments(id2, commentsDTO2);

        final UUID id3 = UUID.randomUUID();
        final List<String> commentaries3 = List.of("test", "test 3");
        final CommentsDTO commentsDTO3 = CommentsDTO.builder().values(commentaries3).build();

        repository.createComments(id3, commentsDTO3);


        final List<String> commentaries4 = List.of("testing", "test 4");
        final CommentsDTO commentsDTO4 = CommentsDTO.builder().values(commentaries4).build();

        repository.updateCommentByBlogPostId(id2, commentsDTO4);

        final Comments comments = repository.readByBlogPostId(id2);

        Assertions.assertNotNull(comments.getId());
        Assertions.assertEquals(id2, comments.getBlogPostId());
        Assertions.assertEquals(List.of("test", "test 2", "testing", "test 4"), comments.getComments());

        final Comments comments2 = repository.readByBlogPostId(id);

        Assertions.assertNotNull(comments2.getId());
        Assertions.assertEquals(id, comments2.getBlogPostId());
        Assertions.assertEquals(commentaries, comments2.getComments());


        final Comments comments3 = repository.readByBlogPostId(id3);

        Assertions.assertNotNull(comments3.getId());
        Assertions.assertEquals(id3, comments3.getBlogPostId());
        Assertions.assertEquals(commentaries3, comments3.getComments());
    }
}
