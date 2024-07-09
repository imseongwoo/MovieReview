package org.teamsparta.moviereview.domain.users.model

import jakarta.persistence.*
import org.teamsparta.moviereview.domain.users.dto.UserUpdateProfileDto
import java.time.LocalDateTime

@Entity
@Table(name = "app_user")
class Users(
    @Column(name = "email", nullable = false)
    val email: String,

    @Column(name = "password")
    var password: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    val role: UserRole = UserRole.USER,

    @Column(name = "nickname", nullable = true)
    var nickname: String,

    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "deleted_at", nullable = true)
    var deletedAt: LocalDateTime = LocalDateTime.now(),
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun updateProfile(profileDto: UserUpdateProfileDto) {
        this.nickname = profileDto.nickname
    }

}
