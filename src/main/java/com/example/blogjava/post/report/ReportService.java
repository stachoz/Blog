package com.example.blogjava.post.report;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ReportService {
    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Transactional
    public void deleteReport(Long reportId){
        Optional<Report> report = reportRepository.findById(reportId);
        report.ifPresent(reportRepository::delete);
    }
}
