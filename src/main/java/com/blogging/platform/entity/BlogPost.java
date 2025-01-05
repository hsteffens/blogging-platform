package com.blogging.platform.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BlogPost implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private UUID id;
    private String title;
    private String content;

    public BlogPost(String title, String content) {
        this.id = UUID.randomUUID();
        this.content = content;
        this.title = title;
    }

}
