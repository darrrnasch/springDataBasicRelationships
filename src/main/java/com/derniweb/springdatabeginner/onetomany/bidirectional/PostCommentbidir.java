package com.derniweb.springdatabeginner.onetomany.bidirectional;

import com.derniweb.springdatabeginner.onetomany.unidirectional.Post;

import javax.persistence.*;

@Entity(name = "PostCommentbidir")
@Table(name = "post_comment_bidir")
public class PostCommentbidir {

    @Id
    @GeneratedValue
    private Long id;

    private String review;

    @ManyToOne(fetch = FetchType.LAZY)
    private Postbidir post;

    //Constructors, getters and setters removed for brevity

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PostCommentbidir )) return false;
        return id != null && id.equals(((PostCommentbidir) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
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

    public Postbidir getPost() {
        return post;
    }

    public void setPost(Postbidir post) {
        this.post = post;
    }

    public PostCommentbidir() {
    }

    public PostCommentbidir(String review) {
        this.review = review;
    }
}
