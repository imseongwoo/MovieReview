package org.teamsparta.moviereview.domain.post.dto

data class UpdatePostRequest(
    val title: String,
    val content: String,
    val category: String
)