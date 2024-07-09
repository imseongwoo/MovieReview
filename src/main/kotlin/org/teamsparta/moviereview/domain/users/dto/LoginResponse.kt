package org.teamsparta.moviereview.domain.users.dto

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String
) {
    companion object {
        fun fromEntity(token: String, refreshToken: String): LoginResponse {
            return LoginResponse(
                accessToken = token,
                refreshToken = refreshToken
            )
        }
    }
}