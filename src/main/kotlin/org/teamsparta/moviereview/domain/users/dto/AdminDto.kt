package org.teamsparta.moviereview.domain.users.dto

import org.teamsparta.moviereview.domain.users.model.Users
import java.time.LocalDateTime

data class AdminDto(
    val userId: Long,
    val email: String,
    val nickname: String,
    val role: String,
    val createdAt: LocalDateTime,
) {
    companion object {
        fun fromEntity(user: Users): AdminDto {
            return AdminDto(
                userId = user.id!!,
                nickname = user.nickname,
                role = user.role.toString(),
                email = user.email,
                createdAt = user.createdAt,
            )
        }
    }
}