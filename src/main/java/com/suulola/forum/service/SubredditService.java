package com.suulola.forum.service;

import com.suulola.forum.dto.SubredditDto;
import com.suulola.forum.exception.SubredditNotFoundException;
import com.suulola.forum.model.Subreddit;
import com.suulola.forum.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubredditService {

    final SubredditRepository subredditRepository;
    final AuthService authService;

    @Autowired
    public SubredditService(SubredditRepository subredditRepository, AuthService authService) {
        this.subredditRepository = subredditRepository;
        this.authService = authService;
    }



    @Transactional(readOnly = true)
    public List<SubredditDto> getAll() {
        return subredditRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SubredditDto getSubreddit(Long id) {
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new SubredditNotFoundException("Subreddit not found with id -" + id));
        return mapToDto(subreddit);
    }

    @Transactional
    public SubredditDto save(SubredditDto subredditDto) {
        Subreddit subreddit = subredditRepository.save(mapToSubreddit(subredditDto));
        subredditDto.id(subreddit.getSubId());
        return subredditDto;
    }

    private SubredditDto mapToDto(Subreddit subreddit) {
        return SubredditDto.builder().name(subreddit.getName())
                .id(subreddit.getSubId())
                .postCount(subreddit.getPosts().size())
                .build();
    }


    private Subreddit mapToSubreddit(SubredditDto subredditDto) {
        return Subreddit.builder().name("/r/" + subredditDto.getName())
                .description(subredditDto.getDescription())
                .user(authService.getCurrentUser())
                .createdDate(Instant.now()).build();
    }

}
