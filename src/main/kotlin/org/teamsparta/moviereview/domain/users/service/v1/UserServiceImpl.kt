package org.teamsparta.moviereview.domain.users.service.v1

import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.teamsparta.moviereview.domain.common.exception.InvalidCredentialException
import org.teamsparta.moviereview.domain.common.exception.ModelNotFoundException
import org.teamsparta.moviereview.domain.common.util.RedisUtils
import org.teamsparta.moviereview.domain.users.dto.LoginRequest
import org.teamsparta.moviereview.domain.users.dto.LoginResponse
import org.teamsparta.moviereview.domain.users.dto.SignUpRequest
import org.teamsparta.moviereview.domain.users.dto.UserDto
import org.teamsparta.moviereview.domain.users.dto.UserUpdateProfileDto
import org.teamsparta.moviereview.domain.users.model.Users
import org.teamsparta.moviereview.domain.users.repository.v1.UserRepository
import org.teamsparta.moviereview.infra.security.jwt.JwtPlugin

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtPlugin: JwtPlugin,
    private val redisUtils: RedisUtils
) : UserService {

    @Transactional
    override fun signUp(request: SignUpRequest): UserDto {
        if (userRepository.findByEmail(request.email) != null) {
            throw InvalidCredentialException("중복된 이메일입니다.")
        }
        if (request.password != request.confirmPassword) {
            throw InvalidCredentialException("비밀번호 확인이 비밀번호와 일치하지 않습니다.")
        }
        val user = userRepository.save(
            Users(
                email = request.email,
                password = passwordEncoder.encode(request.password),
                nickname = request.nickname
            )
        )
        return UserDto.fromEntity(user)
    }

    override fun signIn(loginRequest: LoginRequest): LoginResponse {
        val user = userRepository.findByEmail(loginRequest.email) ?: throw ModelNotFoundException("Users", null)

        if (user.email != loginRequest.email || !passwordEncoder.matches(loginRequest.password, user.password)) {
            throw InvalidCredentialException()
        }
        return LoginResponse.fromEntity(
            jwtPlugin.generateAccessToken(
                subject = user.id.toString(),
                email = user.email,
                role = user.role.toString()
            )
        )
    }

    @Transactional
    override fun updateProfile(profile: UserUpdateProfileDto, userId: Long): UserDto {
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("Users", userId)
        user.updateProfile(profile)
        return UserDto.fromEntity(user)
    }

    override fun signOut(token: String) {
        redisUtils.setDataExpire(token, "blacklisted")
    }

    fun isTokenBlacklisted(token: String): Boolean {
        return redisUtils.getData(token) != null
    }

}