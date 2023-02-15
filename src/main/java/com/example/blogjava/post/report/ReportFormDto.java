package com.example.blogjava.post.report;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Size;

public class ReportFormDto {
    @Size(max = 200)
    @Nullable
    private String cause;

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }
}