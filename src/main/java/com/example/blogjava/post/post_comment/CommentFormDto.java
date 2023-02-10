package com.example.blogjava.post.post_comment;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class CommentFormDto {
    @Size(max = 500)
    @NotEmpty
    private String content;
    private String authorName;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
