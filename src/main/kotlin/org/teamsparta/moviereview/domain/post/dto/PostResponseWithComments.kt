package org.teamsparta.moviereview.domain.post.dto

import org.teamsparta.moviereview.domain.post.model.Post
import java.time.LocalDateTime

data class PostResponseWithComments(
    val id: Long,
    val title: String,
    val nickname: String,
    val content: String,
    val category: String,
    val thumbUpCount: Long,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    // val commentList: List<CommentResponse>
) {
    companion object {
        fun from(post: Post,
            // commentList: List<CommentResponse>
        ): PostResponse {
            return PostResponse(
                id = post.id!!,
                title = post.title,
                nickname = "nickname",
                content = post.content,
                category = post.category.name,
                thumbUpCount = 0,
                createdAt = post.createdAt,
                updatedAt = post.updatedAt,
                // commentList = commentList
            )
        }
    }
}
