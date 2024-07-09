package org.teamsparta.moviereview.infra.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.teamsparta.moviereview.domain.common.exception.InvalidCredentialException
import org.teamsparta.moviereview.domain.users.dto.LoginResponse
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.time.Instant
import java.util.*

@Component
class JwtPlugin(
    @Value("\${auth.jwt.issuer}") private val issuer: String,
    @Value("\${auth.jwt.secret}") private val secret: String,
    @Value("\${auth.jwt.accessTokenExpirationHour}") private val accessTokenExpirationHour: Long,
    @Value("\${auth.jwt.refreshTokenExpirationHour}") private val refreshTokenExpirationHour: Long,
) {
    fun validateToken(token: String): Result<Jws<Claims>> {
        return kotlin.runCatching {
            val key = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token)
        }
    }

    fun generateAccessToken(subject: String, email: String, role: String): String {
        return generateToken(subject, email, role, Duration.ofHours(accessTokenExpirationHour))
    }

    fun generateRefreshToken(subject: String, email: String, role: String): String {
        return generateToken(subject, email, role, Duration.ofHours(refreshTokenExpirationHour))
    }

    private fun generateToken(subject: String, email: String, role: String, expirationPeriod: Duration?): String {
        val claims: Claims = Jwts.claims()
            .add(mapOf("email" to email))
            .add(mapOf("role" to role))
            .build()

        val key = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))
        val now = Instant.now()

        return Jwts.builder()
            .subject(subject)
            .issuer(issuer)
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plus(expirationPeriod)))
            .claims(claims)
            .signWith(key)
            .compact()
    }

    fun validateRefreshTokenAndCreateToken(refreshToken: String): LoginResponse {
        val claims = validateToken(refreshToken).getOrElse { throw InvalidCredentialException("Invalid Refresh Token") }.body
        val subject = claims.subject
        val email = claims["email"].toString()
        val role = claims["role"].toString()

        val newAccessToken = generateAccessToken(subject, email, role)
        val newRefreshToken = generateRefreshToken(subject, email, role)

        return LoginResponse(newAccessToken, newRefreshToken)
    }


}