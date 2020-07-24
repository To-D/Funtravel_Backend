package pers.jaxon.funtravel.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="topics")
public class Topic {

    @ManyToMany(mappedBy = "topics")
    private Set<Picture> pictures;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="topic")
    private String topic;
}
