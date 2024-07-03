package org.teamsparta.moviereview.domain.comment.service.v1

import org.springframework.stereotype.Service
import org.teamsparta.moviereview.domain.comment.dto.CommentCreateRequest
import org.teamsparta.moviereview.domain.comment.dto.CommentResponse
import org.teamsparta.moviereview.domain.comment.dto.CommentUpdateRequest

@Service
class CommentServiceImpl : CommentService {
    override fun createComment(request: CommentCreateRequest): CommentResponse {
        TODO("Not yet implemented")
    }

    override fun updateComment(commentId: Long, request: CommentUpdateRequest): CommentResponse {
        TODO("Not yet implemented")
    }

    override fun deleteComment(commentId: Long) {
        TODO("Not yet implemented")
    }
}