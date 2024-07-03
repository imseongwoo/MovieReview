package org.teamsparta.moviereview.domain.comment.model

import jakarta.persistence.*
import org.teamsparta.moviereview.domain.comment.dto.CommentResponse
import org.teamsparta.moviereview.domain.comment.dto.CommentUpdateRequest
import org.teamsparta.moviereview.domain.post.model.Post
import org.teamsparta.moviereview.domain.users.model.Users
import java.time.LocalDateTime

@Entity
@Table(name = "comment")
class Comment(
    @Column
    var content: String,

    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime,

    @Column(name = "deleted_at")
    var deletedAt: LocalDateTime,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    var post: Post,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: Users,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    fun toResponse(): CommentResponse {
        return CommentResponse(
            id = id!!,
            content = content,
            createdAt = createdAt,
            nickname = user.nickname, // 닉네임 주입 필요
        )
    }

    fun updateContent(commentUpdateRequest: CommentUpdateRequest) {
        this.content = commentUpdateRequest.content
        this.updatedAt = LocalDateTime.now()
    }

    fun delete() {
        this.deletedAt = LocalDateTime.now()
    }
}