//package com.suulola.forum.model;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.springframework.lang.Nullable;
//
//import javax.persistence.*;
//import javax.validation.constraints.NotBlank;
//import java.time.Instant;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Builder
//public class Post {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long postId;
//
//    @Nullable
//    @Lob
//    private String description;
//
//    @NotBlank(message = "Post Name cannot be empty or null")
//    private String postName;
//
//    @Nullable
//    private String url;
//
//    private Integer voteCount;
//
//    private Instant createdDate;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "userId", referencedColumnName = "userId")
//    private User user;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "subId", referencedColumnName = "subId")
//    private Subreddit subreddit;
//
//}
