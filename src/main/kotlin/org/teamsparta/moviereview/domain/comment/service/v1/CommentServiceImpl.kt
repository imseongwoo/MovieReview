package org.teamsparta.moviereview.domain.comment.service.v1

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.teamsparta.moviereview.domain.comment.dto.CommentCreateRequest
import org.teamsparta.moviereview.domain.comment.dto.CommentResponse
import org.teamsparta.moviereview.domain.comment.dto.CommentUpdateRequest
import org.teamsparta.moviereview.domain.comment.dto.CommentUpdateResponse
import org.teamsparta.moviereview.domain.comment.model.Comment
import org.teamsparta.moviereview.domain.comment.repository.v1.CommentRepository
import org.teamsparta.moviereview.domain.common.exception.AccessDeniedException
import org.teamsparta.moviereview.domain.common.exception.ModelNotFoundException
import org.teamsparta.moviereview.domain.post.model.Post
import org.teamsparta.moviereview.domain.post.repository.v1.PostRepository
import org.teamsparta.moviereview.domain.users.model.Users
import org.teamsparta.moviereview.domain.users.repository.v1.UserRepository

@Service
class CommentServiceImpl(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
    private val commentRepository: CommentRepository
) : CommentService {
    override fun createComment(postId: Long, request: CommentCreateRequest, email: String): CommentResponse {
        val post: Post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("post", postId)
        val user: Users = userRepository.findByEmail(email) ?: throw IllegalStateException()

        val comment = Comment(
            content = request.content,
            post = post,
            user = user
        )
        commentRepository.save(comment)
        return CommentResponse.fromEntity(comment)
    }

    @Transactional
    override fun updateComment(commentId: Long, request: CommentUpdateRequest, email: String): CommentUpdateResponse {
        val comment: Comment =
            commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("comment", commentId)
        val user: Users = userRepository.findByEmail(email) ?: throw IllegalStateException()
        if (!comment.checkPermission(user)) {
            comment.updateContent(request)
        } else throw AccessDeniedException("업데이트 권한이 없습니다.")

        return CommentUpdateResponse.fromEntity(comment)
    }

    @Transactional
    override fun deleteComment(commentId: Long, email: String) {
        val comment: Comment =
            commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("comment", commentId)
        val user: Users = userRepository.findByEmail(email) ?: throw IllegalStateException()
        if (!comment.checkPermission(user)) {
            comment.softDelete()
        } else throw AccessDeniedException("삭제 권한이 없습니다.")
    }
}