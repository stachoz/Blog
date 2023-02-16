package com.example.blogjava.post.report;

public class ReportAdminDtoMapper {
    public static ReportAdminDto map(Report report){
        ReportAdminDto dto = new ReportAdminDto();
        dto.setCause(report.getCause());
        dto.setPostTitle(report.getPost().getTitle());
        dto.setPostId(report.getPost().getId());
        return dto;
    }
}
