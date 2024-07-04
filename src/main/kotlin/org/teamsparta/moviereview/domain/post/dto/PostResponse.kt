package org.teamsparta.moviereview.domain.post.dto

import org.teamsparta.moviereview.domain.post.model.Post
import java.time.LocalDateTime

data class PostResponse(
    val id: Long,
    val title: String,
    val nickname: String,
    val content: String,
    val category: String,
    val thumbUpCount: Long,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun from(post: Post, thumbUpCount: Long): PostResponse {
            return PostResponse(
                id = post.id!!,
                title = post.title,
                nickname = post.user.nickname,
                content = post.content,
                category = post.category.name,
                thumbUpCount = thumbUpCount,
                createdAt = post.createdAt,
                updatedAt = post.updatedAt
            )
        }
    }
}