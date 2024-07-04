package org.teamsparta.moviereview.domain.admin.service.v1

import org.teamsparta.moviereview.domain.users.dto.AdminDto
import org.teamsparta.moviereview.domain.users.dto.SignUpRequest
import org.teamsparta.moviereview.domain.users.dto.UserDto

interface AdminService {
    fun createAdmin(request: SignUpRequest): AdminDto
}