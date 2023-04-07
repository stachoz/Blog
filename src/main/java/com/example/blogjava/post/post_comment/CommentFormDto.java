package com.example.blogjava.post.post_comment;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.context.SecurityContextHolder;

public class CommentFormDto {
    @Size(max = 500, min = 1)
    @NotEmpty
    private String content;
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
