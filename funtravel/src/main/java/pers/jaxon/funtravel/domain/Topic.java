package pers.jaxon.funtravel.domain;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;


@Entity
@Table(name="topics")
public class Topic {
    public Topic(){}

    public Topic(String topic){
        this.topic = topic;
        this.pictures = new LinkedList<>();
    }
    // 与图片的多对多关系
    @ManyToMany(mappedBy = "topics")
    private List<Picture> pictures;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="topic")
    private String topic;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }
}
