package com.example.blogjava.user;

import com.example.blogjava.crypto.Coin;
import com.example.blogjava.post.Post;
import com.example.blogjava.post.post_comment.Comment;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "application_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(min = 3, max = 50)
    private String username;
    @NotNull
    @Size(min = 5, max = 80)
    @Email
    private String email;
    @NotNull
    @Size(min = 4, max = 200)
    private String password;

    @NotNull
    private boolean postVerification = false;
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<UserRole> userRoles = new HashSet<>();

    @OneToMany(mappedBy = "user",
    cascade = CascadeType.REMOVE)
    private Set<Post> posts = new HashSet<>();

    @OneToMany(mappedBy = "user",
    cascade = CascadeType.REMOVE)
    private Set<Comment> comments = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "user_coins",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "coin_id")
    )
    private Set<Coin> coins = new HashSet<>();


    public User(String username, String email, String password, boolean postVerification) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.postVerification = postVerification;
    }

    public Set<Coin> getCoins() {
        return coins;
    }

    public void addCoin(Coin coin){
        coins.add(coin);
    }

    public void setCoins(Set<Coin> coins) {
        this.coins = coins;
    }

    public User(){};


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getPostVerification() {
        return postVerification;
    }

    public void setPostVerification(boolean postVerification) {
        this.postVerification = postVerification;
    }


    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }
}
