package pers.jaxon.funtravel.domain;

import javax.persistence.*;

@Entity
@Table(name="comments")
public class Comment {

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "picture_id")
    private Picture picture;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="comment")
    private String comment;
}
