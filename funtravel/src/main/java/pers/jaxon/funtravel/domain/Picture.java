package pers.jaxon.funtravel.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="pictures")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Picture {

    // 对user的多对一关系
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "uploader")
    private User uploader;

    // 对topics的多对多关系
    @ManyToMany
    private Set<Topic> topics;

    // 对comment的一对多关系
    @OneToMany(mappedBy = "picture")
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

//    @Column(name="release_time")
//    private Date releaseTime;

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


//    public Date getReleaseTime() {
//        return releaseTime;
//    }
//
//    public void setReleaseTime(Date releaseTime) {
//        this.releaseTime = releaseTime;
//    }


}
