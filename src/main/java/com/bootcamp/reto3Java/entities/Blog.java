package com.bootcamp.reto3Java.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(value="blogs")
public class Blog {

    @Id
    private String id;
    private String name;
    private String description;
    private String url;
    private String status;
    private String authorId;
}
