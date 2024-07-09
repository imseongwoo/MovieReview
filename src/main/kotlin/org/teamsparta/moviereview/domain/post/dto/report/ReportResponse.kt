package org.teamsparta.moviereview.domain.post.dto.report

import org.teamsparta.moviereview.domain.post.dto.PostResponse
import org.teamsparta.moviereview.domain.post.model.report.Report
import org.teamsparta.moviereview.domain.users.dto.UserDto

data class ReportResponse(
    val id: Long,
    val reportType: String,
    val description: String,
    val userId: Long,
    val postId: Long
){
    companion object {
        fun from(report: Report): ReportResponse {
            return ReportResponse(
                id = report.id!!,
                reportType = report.reportType.name,
                description = report.description,
                userId = report.user.id!!,
                postId = report.post.id!!
            )
        }
    }
}