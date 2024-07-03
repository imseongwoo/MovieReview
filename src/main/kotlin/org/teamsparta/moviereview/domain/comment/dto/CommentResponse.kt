package org.teamsparta.moviereview.domain.comment.dto

import org.teamsparta.moviereview.domain.comment.model.Comment
import java.time.LocalDateTime

data class CommentResponse(
    val id: Long,
    val nickname: String,
    val content: String,
    val createdAt: LocalDateTime,
){
    companion object {
        fun fromEntity(comment: Comment): CommentResponse {
            return CommentResponse(
                id = comment.id!!,
                nickname = comment.user.nickname,
                content = comment.content,
                createdAt = comment.createdAt,
            )
        }
    }
}