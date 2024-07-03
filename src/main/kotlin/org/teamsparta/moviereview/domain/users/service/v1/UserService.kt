package org.teamsparta.moviereview.domain.users.service.v1

import org.teamsparta.moviereview.domain.users.dto.SignUpRequest
import org.teamsparta.moviereview.domain.users.dto.UserDto

interface UserService {
    fun signUp(request: SignUpRequest): UserDto
}