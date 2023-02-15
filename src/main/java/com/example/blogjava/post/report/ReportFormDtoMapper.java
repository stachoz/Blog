package com.example.blogjava.post.report;

public class ReportFormDtoMapper {
    public static Report map(ReportFormDto dto){
        Report report = new Report();
        report.setCause(dto.getCause());
        return report;
    }
}
