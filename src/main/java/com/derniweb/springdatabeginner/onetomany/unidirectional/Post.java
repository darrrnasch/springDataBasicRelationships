package com.derniweb.springdatabeginner.onetomany.unidirectional;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Post")
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<PostComment> comments = new ArrayList<>();

    //Constructors, getters and setters removed for brevity

    public Post() {
    }

    public Post(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<PostComment> getComments() {
        return comments;
    }

    public void setComments(List<PostComment> comments) {
        this.comments = comments;
    }
}