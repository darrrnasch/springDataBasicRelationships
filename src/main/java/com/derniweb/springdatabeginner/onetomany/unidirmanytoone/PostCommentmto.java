package com.derniweb.springdatabeginner.onetomany.unidirmanytoone;

import com.derniweb.springdatabeginner.onetomany.bidirectional.Postbidir;

import javax.persistence.*;

@Entity(name = "PostCommentmto")
@Table(name = "post_comment_mto")
public class PostCommentmto {

    @Id
    @GeneratedValue
    private Long id;

    private String review;

    /*
        without join colummn
        SELECT * FROM POST_COMMENT_MTO;
        ID  	REVIEW  	POST_ID
     */
    /*
        with joincolumn
        SELECT * FROM POST_COMMENT_MTO;
        ID  	REVIEW  	POST_MTO_ID
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_mto_id")
    private Postmto post;


}
