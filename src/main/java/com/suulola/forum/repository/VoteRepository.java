//package com.suulola.forum.repository;
//
//import com.suulola.forum.model.Post;
//import com.suulola.forum.model.Vote;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.Optional;
//
//@Repository
//public interface VoteRepository extends JpaRepository<Vote, Long> {
//
//    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
//
//}
