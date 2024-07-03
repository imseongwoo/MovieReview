package org.teamsparta.moviereview.domain.users.dto

data class LoginResponse(
    val accessToken: String
) {
    companion object {
        fun fromEntity(token: String): LoginResponse {
            return LoginResponse(
                accessToken = token
            )
        }
    }
}