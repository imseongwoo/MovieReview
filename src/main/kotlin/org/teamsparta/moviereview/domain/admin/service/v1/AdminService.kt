package org.teamsparta.moviereview.domain.admin.service.v1

import org.teamsparta.moviereview.domain.post.dto.UpdateCategoryRequest
import org.teamsparta.moviereview.domain.post.dto.report.ReportResponse
import org.teamsparta.moviereview.domain.users.dto.AdminDto
import org.teamsparta.moviereview.domain.users.dto.SignUpRequest

interface AdminService {
    fun createAdmin(request: SignUpRequest): AdminDto
    fun updatePostCategory(postId: Long, request: UpdateCategoryRequest)
    fun deleteReportedPost(reportId: Long)
    fun rejectReport(reportId: Long)
    fun getReportList(): List<ReportResponse>
}