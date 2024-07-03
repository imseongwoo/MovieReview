package org.teamsparta.moviereview.domain.post.dto

data class CreatePostRequest(
    val title: String,
    val content: String,
    val category: String
)