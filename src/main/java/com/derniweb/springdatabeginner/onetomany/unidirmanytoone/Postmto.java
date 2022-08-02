package com.derniweb.springdatabeginner.onetomany.unidirmanytoone;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/*
https://vladmihalcea.com/the-best-way-to-map-a-onetomany-association-with-jpa-and-hibernate/
you cannot limit the size of a @OneToMany collection like it would be the case if you used query-level pagination.

Therefore, most of the time, the @ManyToOne annotation on the child side is everything you need. But then, how do you get the child entities associated with a Post entity?

Well, all you need is just a single JPQL query:
    List<PostComment> comments = entityManager.createQuery(
        "select pc " +
        "from PostComment pc " +
        "where pc.post.id = :postId", PostComment.class)
    .setParameter( "postId", 1L )
    .getResultList();
 */

@Entity(name = "Postmto")
@Table(name = "post_mto")
public class Postmto {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

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

    public Postmto(String title) {
        this.title = title;
    }

    public Postmto() {
    }
}
