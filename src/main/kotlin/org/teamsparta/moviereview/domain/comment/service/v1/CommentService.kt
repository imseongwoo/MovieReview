package org.teamsparta.moviereview.domain.comment.service.v1

import org.teamsparta.moviereview.domain.comment.dto.CommentCreateRequest
import org.teamsparta.moviereview.domain.comment.dto.CommentResponse
import org.teamsparta.moviereview.domain.comment.dto.CommentUpdateRequest
import org.teamsparta.moviereview.domain.comment.dto.CommentUpdateResponse

interface CommentService {
    fun createComment(postId: Long, request: CommentCreateRequest, email: String): CommentResponse
    fun updateComment(commentId: Long, request: CommentUpdateRequest, email: String): CommentUpdateResponse
    fun deleteComment(commentId: Long, email: String)
}