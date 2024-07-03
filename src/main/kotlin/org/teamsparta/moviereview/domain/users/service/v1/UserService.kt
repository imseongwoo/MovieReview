package org.teamsparta.moviereview.domain.users.service.v1

import org.teamsparta.moviereview.domain.users.dto.LoginRequest
import org.teamsparta.moviereview.domain.users.dto.LoginResponse
import org.teamsparta.moviereview.domain.users.dto.SignUpRequest
import org.teamsparta.moviereview.domain.users.dto.UserDto

interface UserService {
    fun signUp(request: SignUpRequest): UserDto
    fun signIn(loginRequest: LoginRequest): LoginResponse
}