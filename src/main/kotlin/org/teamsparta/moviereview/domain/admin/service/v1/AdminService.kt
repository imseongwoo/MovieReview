package org.teamsparta.moviereview.domain.admin.service.v1

import org.teamsparta.moviereview.domain.post.dto.PostResponse
import org.teamsparta.moviereview.domain.post.dto.UpdateCategoryRequest
import org.teamsparta.moviereview.domain.users.dto.AdminDto
import org.teamsparta.moviereview.domain.users.dto.SignUpRequest
import org.teamsparta.moviereview.infra.security.UserPrincipal

interface AdminService {
    fun createAdmin(request: SignUpRequest): AdminDto
    fun updatePostCategory(postId: Long, request: UpdateCategoryRequest)
}