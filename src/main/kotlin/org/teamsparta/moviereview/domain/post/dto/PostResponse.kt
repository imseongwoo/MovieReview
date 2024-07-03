package org.teamsparta.moviereview.domain.post.dto

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
)