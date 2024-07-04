package org.teamsparta.moviereview.domain.admin.service.v1

import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.teamsparta.moviereview.domain.common.exception.InvalidCredentialException
import org.teamsparta.moviereview.domain.common.exception.ModelNotFoundException
import org.teamsparta.moviereview.domain.post.dto.PostResponse
import org.teamsparta.moviereview.domain.post.dto.UpdateCategoryRequest
import org.teamsparta.moviereview.domain.post.repository.v1.PostRepository
import org.teamsparta.moviereview.domain.post.repository.v1.report.ReportRepository
import org.teamsparta.moviereview.domain.users.dto.AdminDto
import org.teamsparta.moviereview.domain.users.dto.SignUpRequest
import org.teamsparta.moviereview.domain.users.model.UserRole
import org.teamsparta.moviereview.domain.users.model.Users
import org.teamsparta.moviereview.domain.users.repository.v1.UserRepository
import org.teamsparta.moviereview.infra.security.UserPrincipal

@Service
class AdminServiceImpl(
    private val userRepository: UserRepository,
    private val postRepository: PostRepository,
    private val reportRepository: ReportRepository,
    private val passwordEncoder: PasswordEncoder
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

    @Transactional
    override fun updatePostCategory(postId: Long, request: UpdateCategoryRequest) {
        val (category) = request
        postRepository.findByIdOrNull(postId)?.apply { this.updateCategory(category) }
            ?: throw ModelNotFoundException("Post", postId)
    }

    @Transactional
    override fun deleteReportedPost(reportId: Long): String {
        reportRepository.findByIdOrNull(reportId)?.apply { this.post.softDelete() }
            ?: throw ModelNotFoundException("Report", reportId)
        return "신고된 게시글이 삭제되었습니다."
    }
}