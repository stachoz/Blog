package com.example.blogjava.post.dto;

import com.example.blogjava.post.Post;

public class PostDtoMapper {
    public static PostDto map(Post post){
        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setTimeAdded(post.getTimeAdded());
        dto.setAuthor(post.getUser().getUsername());
        return dto;
    }
}
