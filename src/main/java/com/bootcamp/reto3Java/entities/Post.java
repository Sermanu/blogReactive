package com.bootcamp.reto3Java.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(value="posts")
public class Post {

    @Id
    private String id;
    private String title;
    private Date date;
    private String status;
    private String content;
    private String blogId;
}
