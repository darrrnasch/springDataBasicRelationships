package com.derniweb.springdatabeginner.onetomany.unidirmanytoone;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

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
