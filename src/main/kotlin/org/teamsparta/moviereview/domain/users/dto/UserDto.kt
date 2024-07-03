package org.teamsparta.moviereview.domain.users.dto

import org.teamsparta.moviereview.domain.users.model.Users
import java.time.LocalDateTime

data class UserDto(
    val userId: Long,
    val email: String,
    val nickname: String,
    val createdAt: LocalDateTime,
) {
    companion object {
        fun fromEntity(user: Users): UserDto {
            return UserDto(
                userId = user.id!!,
                nickname = user.nickname,
                email = user.email,
                createdAt = user.createdAt,
            )
        }
    }
}
