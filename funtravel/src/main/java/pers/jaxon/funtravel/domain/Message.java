package pers.jaxon.funtravel.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="messages")
public class Message {
    // 对user的多对一通知关系
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "target")
    private User target;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "sent_time")
    private Date sent_time;

    @Column(name = "source")
    private String sourceName;

    @Column(name = "type")
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getTarget() {
        return target;
    }

    public void setTarget(User target) {
        this.target = target;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSent_time() {
        return sent_time;
    }

    public void setSent_time(Date sent_time) {
        this.sent_time = sent_time;
    }
}
