package org.teamsparta.moviereview.domain.comment.service.v1

import org.teamsparta.moviereview.domain.comment.dto.CommentCreateRequest
import org.teamsparta.moviereview.domain.comment.dto.CommentResponse
import org.teamsparta.moviereview.domain.comment.dto.CommentUpdateRequest

interface CommentService {
    fun createComment(postId: Long, request: CommentCreateRequest, email: String): CommentResponse
    fun updateComment(commentId: Long, request: CommentUpdateRequest): CommentResponse
    fun deleteComment(commentId: Long)
}