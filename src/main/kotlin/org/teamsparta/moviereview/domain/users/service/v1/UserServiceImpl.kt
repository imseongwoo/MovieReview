package org.teamsparta.moviereview.domain.users.service.v1

import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.teamsparta.moviereview.domain.common.InvalidCredentialException
import org.teamsparta.moviereview.domain.common.exception.ModelNotFoundException
import org.teamsparta.moviereview.domain.users.dto.SignUpRequest
import org.teamsparta.moviereview.domain.users.dto.UserDto
import org.teamsparta.moviereview.domain.users.dto.UserUpdateProfileDto
import org.teamsparta.moviereview.domain.users.model.Users
import org.teamsparta.moviereview.domain.users.repository.v1.UserRepository

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
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

    @Transactional
    override fun updateProfile(profile: UserUpdateProfileDto, userId: Long): UserDto {
        val user = userRepository.findByIdOrNull(userId) ?: throw ModelNotFoundException("Users", userId)
        user.updateProfile(profile)
        return UserDto.fromEntity(user)
    }
}