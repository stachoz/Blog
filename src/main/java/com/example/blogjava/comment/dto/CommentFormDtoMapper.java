package com.example.blogjava.comment.dto;

import com.example.blogjava.comment.Comment;

public class CommentFormDtoMapper {
    public static CommentFormDto map(Comment comment){
        CommentFormDto dto = new CommentFormDto();
        dto.setContent(dto.getContent());
        return dto;
    }

    public static Comment map(CommentFormDto dto){
        Comment comment = new Comment();
        comment.setContent(dto.getContent());
        return comment;
    }
}
