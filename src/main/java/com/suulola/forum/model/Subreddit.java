package com.suulola.forum.model;

import com.suulola.forum.dto.SubredditDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
public class Subreddit {


    public Subreddit() {
    }

    public Long getSubId() {
        return subId;
    }

    public Subreddit subId(Long subId) {
        this.subId = subId;
        return this;
    }

    public String getName() {
        return name;
    }

    public Subreddit name(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Subreddit description(String description) {
        this.description = description;
        return this;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Subreddit createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Subreddit user(User user) {
        this.user = user;
        return this;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public Subreddit posts(List<Post> posts) {
        this.posts = posts;
        return this;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subId;

    @NotBlank(message = "Community name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    private Instant createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Post> posts;

    public static Subreddit builder() {
        return new Subreddit();
    }


    public Subreddit build() {
        Subreddit subreddit = new Subreddit();
        subreddit.subId = this.subId;
        subreddit.createdDate = this.createdDate;
        subreddit.description = this.description;
        subreddit.user = this.user;
        subreddit.posts = this.posts;
        return subreddit;
    }



}
