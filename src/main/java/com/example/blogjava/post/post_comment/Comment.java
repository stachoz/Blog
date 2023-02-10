package com.example.blogjava.post.post_comment;

import com.example.blogjava.post.Post;
import com.example.blogjava.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(max = 500)
    @NotEmpty
    private String content;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeAdded;
    @ManyToOne
    @JoinColumn(
            name = "author_id",
            referencedColumnName = "id"
    )
    private User user;

    @ManyToOne
    @JoinColumn(
            name = "post_id",
            referencedColumnName = "id"
    )
    private Post post;

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public Date getTimeAdded() {
        return timeAdded;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
