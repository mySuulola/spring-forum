//package com.suulola.forum.model;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//import javax.validation.constraints.NotBlank;
//import java.time.Instant;
//import java.util.List;
//
//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class Subreddit {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long subId;
//
//    @NotBlank(message = "Community name is required")
//    private String name;
//
//    @NotBlank(message = "Description is required")
//    private String description;
//
//    private Instant createdDate;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    private User user;
//
//    @OneToMany(fetch = FetchType.LAZY)
//    private List<Post> posts;
//
//
//
//}
