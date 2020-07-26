package pers.jaxon.funtravel.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name="pictures")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Picture {
    // 对user的多对一上传关系
    @ManyToOne
    @JoinColumn(name = "uploader")
    private User uploader;

    // 与user的多对多收藏关系
    @ManyToMany(mappedBy = "collections",fetch=FetchType.EAGER)
    private Set<User> collectors;

    // 与topics的多对多关系
    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(
            joinColumns = {@JoinColumn(name = "picture_id")},
            inverseJoinColumns = {@JoinColumn(name = "topic_id")}
    )
    private Set<Topic> topics;

    // 对comment的一对多关系
    @OneToMany(mappedBy = "picture",fetch=FetchType.EAGER)
    private Set<Comment> comments;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="title",nullable = false)
    private String title;

    @Column(name="author",nullable = false)
    private String author;

    @Column(name="intro",nullable = false)
    private String intro;

    @Column(name="collection_count",nullable = false)
    private Long collectionCount;

    @Column(name="nation",nullable = false)
    private String nation;

    @Column(name="city",nullable = false)
    private String city;

    @Column(name="url",nullable = false)
    private String url;

    @Column(name="upload_time")
    private Date uploadTime;

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public void setCollectionCount(Long collectionCount) {
        this.collectionCount = collectionCount;
    }

    public Long getCollectionCount() {
        return collectionCount;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public User getUploader() {
        return uploader;
    }

    @JsonBackReference
    public void setUploader(User uploader) {
        this.uploader = uploader;
    }

    public Set<User> getCollectors() {
        return collectors;
    }

    @JsonBackReference
    public void setCollectors(Set<User> collectors) {
        this.collectors = collectors;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "uploader=" + uploader +
                ", collectors=" + collectors +
                ", topics=" + topics +
                ", comments=" + comments +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", intro='" + intro + '\'' +
                ", collectionCount=" + collectionCount +
                ", nation='" + nation + '\'' +
                ", city='" + city + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public Set<Topic> getTopics() {
        return topics;
    }

    @JsonBackReference
    public void setTopics(Set<Topic> topics) {
        this.topics = topics;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }


}
