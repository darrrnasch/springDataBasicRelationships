package com.derniweb.springdatabeginner.onetomany.unidirwithjoincol;

import com.derniweb.springdatabeginner.onetomany.unidirectional.PostComment;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Postwjc")
@Table(name = "postwjc")
public class Postwjc {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "postwjc_id")
    private List<PostCommentwjc> comments = new ArrayList<>();

    //Constructors, getters and setters removed for brevity

    public Postwjc() {
    }

    public Postwjc(String title) {
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

    public List<PostCommentwjc> getComments() {
        return comments;
    }

    public void setComments(List<PostCommentwjc> comments) {
        this.comments = comments;
    }
}
