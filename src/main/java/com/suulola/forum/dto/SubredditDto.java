package com.suulola.forum.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class SubredditDto {

        private Long id;
        private String name;
        private String description;
        private Integer postCount;

    private SubredditDto() { }

        public Long id() {
            return id;
        }

        public SubredditDto id(Long id) {
            this.id = id;
            return this;
        }

        public String name() {
            return name;
        }

        public SubredditDto name(String name) {
            this.name = name;
            return this;
        }

        public String description() {
            return description;
        }

        public SubredditDto description(String description) {
            this.description = description;
            return this;
        }

        public Integer postCount() {
            return postCount;
        }

        public SubredditDto postCount(Integer postCount) {
            this.postCount = postCount;
            return this;
        }

        public SubredditDto build() {
            SubredditDto subredditDto = new SubredditDto();
            subredditDto.name = this.name;
            subredditDto.id = this.id;
            subredditDto.description = this.description;
            subredditDto.postCount = this.postCount;
            return subredditDto;
        }



    public static SubredditDto builder() {
        return new SubredditDto();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getPostCount() {
        return postCount;
    }
}