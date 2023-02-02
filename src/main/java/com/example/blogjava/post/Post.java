package com.example.blogjava.post;

import com.example.blogjava.comment.Comment;
import com.example.blogjava.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100)
    private String title;

    @Size(max = 2000)
    @NotEmpty
    private String content;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeAdded;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private User user;

    @OneToMany()
    @JoinColumn(name = "comment_id")
    private List<Comment> comments = new ArrayList<>();

    public Date getTimeAdded() {
        return timeAdded;
    }

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}