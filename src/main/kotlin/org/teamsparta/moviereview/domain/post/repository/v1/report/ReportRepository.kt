package org.teamsparta.moviereview.domain.post.repository.v1.report

import org.springframework.data.jpa.repository.JpaRepository
import org.teamsparta.moviereview.domain.post.model.report.Report

interface ReportRepository: JpaRepository<Report, Long> {
}