package com.blogging.platform.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comments implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private UUID id;
    private UUID blogPostId;
    private List<String> comments;

    public Comments(UUID blogPostId, List<String> comments) {
        this.id = UUID.randomUUID();
        this.blogPostId = blogPostId;
        this.comments = comments;
    }
}
