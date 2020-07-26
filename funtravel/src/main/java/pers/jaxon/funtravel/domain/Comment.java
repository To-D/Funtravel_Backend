package pers.jaxon.funtravel.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="comments")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer","handler"})
public class Comment {
    @ManyToOne
    @JoinColumn(name = "picture_id")
    private Picture picture;

    @Column(name="username")
    private String username;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="comment")
    private String comment;

    @Column(name="time")
    private Date time;

    public Comment(Picture picture,String username, String comment, Date time) {
        this.picture=picture;
        this.username = username;
        this.comment = comment;
        this.time = time;
    }

    public Comment(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Picture getPicture() {
        return picture;
    }

    @JsonBackReference
    public void setPicture(Picture picture) {
        this.picture = picture;
    }
}
