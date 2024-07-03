package org.teamsparta.moviereview.domain.comment.controller.v1

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.teamsparta.moviereview.domain.comment.dto.CommentCreateRequest
import org.teamsparta.moviereview.domain.comment.dto.CommentResponse
import org.teamsparta.moviereview.domain.comment.dto.CommentUpdateRequest
import org.teamsparta.moviereview.domain.comment.service.v1.CommentService
import org.teamsparta.moviereview.domain.users.model.Users

@RequestMapping("/api/v1/comments")
@RestController
class CommentController(
    private val commentService: CommentService
) {
    @PostMapping("/{postId}")
    fun createComment(
        @AuthenticationPrincipal user: Users,
        @PathVariable postId: Long,
        @RequestBody request: CommentCreateRequest
    ): ResponseEntity<CommentResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(postId, request, user.email))
    }

    @PutMapping("/{commentId}")
    fun updateComment(
        @PathVariable commentId: Long,
        @RequestBody request: CommentUpdateRequest
    ): ResponseEntity<CommentResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.updateComment(commentId, request))
    }

    @DeleteMapping("/{commentId}")
    fun deleteComment(
        @PathVariable commentId: Long,
    ):ResponseEntity<Unit> {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(commentService.deleteComment(commentId))
    }
}