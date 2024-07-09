package org.teamsparta.moviereview.domain.comment.dto

import org.teamsparta.moviereview.domain.comment.model.Comment
import java.time.LocalDateTime

data class CommentUpdateResponse(
    val id: Long,
    val nickname: String,
    val content: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
    companion object {
        fun fromEntity(comment: Comment): CommentUpdateResponse {
            return CommentUpdateResponse(
                id = comment.id!!,
                nickname = comment.user.nickname,
                content = comment.content,
                createdAt = comment.createdAt,
                updatedAt = comment.updatedAt,
            )
        }
    }
}
