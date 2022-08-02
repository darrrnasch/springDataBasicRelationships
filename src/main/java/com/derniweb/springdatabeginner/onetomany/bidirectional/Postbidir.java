package com.derniweb.springdatabeginner.onetomany.bidirectional;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Postbidir")
@Table(name = "post_bidir")
public class Postbidir {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    @OneToMany(
            mappedBy = "post",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<PostCommentbidir> comments = new ArrayList<>();

    //Constructors, getters and setters removed for brevity


    public Postbidir(String title) {
        this.title = title;
    }

    public Postbidir() {
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

    public List<PostCommentbidir> getComments() {
        return comments;
    }

    public void setComments(List<PostCommentbidir> comments) {
        this.comments = comments;
    }

    public void addComment(PostCommentbidir comment) {
        comments.add(comment);
        comment.setPost(this);
    }

    public void removeComment(PostCommentbidir comment) {
        comments.remove(comment);
        comment.setPost(null);
    }
}
