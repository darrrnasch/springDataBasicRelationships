package com.derniweb.springdatabeginner.onetomany.unidirwithjoincol;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "PostCommentwjc")
@Table(name = "post_commentwjc")
public class PostCommentwjc {

    @Id
    @GeneratedValue
    private Long id;

    private String review;

    //Constructors, getters and setters removed for brevity

    public PostCommentwjc(String review) {
        this.review = review;
    }

    public PostCommentwjc() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
