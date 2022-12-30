package com.example.blogjava.post.dto;

import com.example.blogjava.post.Post;

public class PostFormDtoMapper {
    public static Post map(PostFormDto postFormDto){
        Post post = new Post();
        post.setTitle(postFormDto.getTitle());
        post.setContent(postFormDto.getContent());
        return post;
    }
}
