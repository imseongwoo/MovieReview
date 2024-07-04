package org.teamsparta.moviereview.domain.post.dto

import org.teamsparta.moviereview.domain.comment.model.Comment
import org.teamsparta.moviereview.domain.post.model.Post

data class PostResponseWithThumbsUpAndComments(
    val post: Post,
    val thumbsUpCount: Long,
    val comments : List<Comment>,
)
