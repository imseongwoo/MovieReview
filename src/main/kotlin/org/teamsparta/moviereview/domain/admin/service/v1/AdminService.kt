package org.teamsparta.moviereview.domain.admin.service.v1

import org.teamsparta.moviereview.domain.users.dto.AdminDto
import org.teamsparta.moviereview.domain.users.dto.SignUpRequest

interface AdminService {
    fun createAdmin(request: SignUpRequest): AdminDto
}