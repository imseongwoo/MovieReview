package org.teamsparta.moviereview.domain.admin.service.v1

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.teamsparta.moviereview.domain.common.InvalidCredentialException
import org.teamsparta.moviereview.domain.users.dto.AdminDto
import org.teamsparta.moviereview.domain.users.dto.SignUpRequest
import org.teamsparta.moviereview.domain.users.model.UserRole
import org.teamsparta.moviereview.domain.users.model.Users
import org.teamsparta.moviereview.domain.users.repository.v1.UserRepository
import org.teamsparta.moviereview.infra.security.jwt.JwtPlugin

@Service
class AdminServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtPlugin: JwtPlugin
) : AdminService {
    @Transactional
    override fun createAdmin(request: SignUpRequest): AdminDto {
        if (userRepository.findByEmail(request.email) != null) {
            throw InvalidCredentialException("중복된 이메일입니다.")
        }
        if (request.password != request.confirmPassword) {
            throw InvalidCredentialException("비밀번호 확인이 비밀번호와 일치하지 않습니다.")
        }
        val admin = userRepository.save(
            Users(
                email = request.email,
                password = passwordEncoder.encode(request.password),
                nickname = request.nickname,
                role = UserRole.ADMIN
            )
        )
        return AdminDto.fromEntity(admin)
    }
}