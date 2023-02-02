package com.example.blogjava.comment.dto;

import com.example.blogjava.comment.Comment;

public class CommentDtoMapper {
    public static CommentDto map(Comment comment){
        CommentDto dto = new CommentDto();
        dto.setContent(comment.getContent());
        dto.setTimeAdded(comment.getTimeAdded());
        dto.setUsername(comment.getUser().getUsername());
        return dto;
    }
}
