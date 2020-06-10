//package com.suulola.forum.model;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.NotNull;
//import java.time.Instant;
//import java.util.List;
//
//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class Vote {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long voteId;
//
//    private VoteType voteType;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "userId", referencedColumnName = "userId")
//    private User user;
//
//    @NotNull
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "postId", referencedColumnName = "postId")
//    private Post post;
//
//
//}
//
