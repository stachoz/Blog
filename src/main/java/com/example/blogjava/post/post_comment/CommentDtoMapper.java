package com.example.blogjava.post.post_comment;

public class CommentDtoMapper {
    public static CommentDto map(Comment comment){
        CommentDto dto = new CommentDto();
        dto.setContent(comment.getContent());
        dto.setTimeAdded(comment.getTimeAdded());
        dto.setAuthorUsername(comment.getUser().getUsername());
        return dto;
    }
}
