package pers.jaxon.funtravel.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="users")
public class User implements UserDetails {

    private static final long serialVersionUID = -6140085056226164016L;

    // 与picture的一对多上传关系
    @OneToMany(mappedBy = "uploader",fetch=FetchType.EAGER,cascade={CascadeType.REMOVE})
    private Set<Picture> uploads;

    @OneToMany(mappedBy = "target",fetch=FetchType.EAGER,cascade={CascadeType.REMOVE})
    private Set<Message> messages;

    // 与picture的一对多收藏关系
    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "collection_id")}
    )
    private Set<Picture> collections;

    // 与user的多对多好友关系
    @ManyToMany
    @JsonIgnore
    private Set<User> friends;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="user_name",nullable = false,unique = true,length=20)
    private String username;

    @Column(name="user_password",nullable = false)
    private String password;

    @Column(name="email",nullable = false,unique = true)
    private String email;

    @Column(name="view",nullable = false)
    private int view;

    @Column(name="register_time",nullable = false)
    private Date registerTime;


    public User() {
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Picture> getUploads() {
        return uploads;
    }

    public void setUploads(Set<Picture> uploads) {
        this.uploads = uploads;
    }

    public Set<Picture> getCollections() {
        return collections;
    }

    public void setCollections(Set<Picture> collections) {
        this.collections = collections;
    }

    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if(o != null)
            return this.toString().equals(o.toString());
        else
            return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
