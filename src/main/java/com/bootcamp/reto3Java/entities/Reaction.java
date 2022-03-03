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
@Document(value="reactions")
public class Reaction {

    @Id
    private String id;
    private String type;
    private Date date;
    private String userId;
    private String postId;
}
