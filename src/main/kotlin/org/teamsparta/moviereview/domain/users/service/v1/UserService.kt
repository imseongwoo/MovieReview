package org.teamsparta.moviereview.domain.users.service.v1

import org.teamsparta.moviereview.domain.users.dto.SignUpRequest
import org.teamsparta.moviereview.domain.users.dto.UserDto
import org.teamsparta.moviereview.domain.users.dto.UserUpdateProfileDto

interface UserService {
    fun signUp(request: SignUpRequest): UserDto
    fun updateProfile(profile: UserUpdateProfileDto, userId: Long): UserDto
}