package org.teamsparta.moviereview.domain.comment.service.v1

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.teamsparta.moviereview.domain.comment.dto.CommentCreateRequest
import org.teamsparta.moviereview.domain.comment.dto.CommentResponse
import org.teamsparta.moviereview.domain.comment.dto.CommentUpdateRequest
import org.teamsparta.moviereview.domain.comment.model.Comment
import org.teamsparta.moviereview.domain.comment.repository.v1.CommentRepository
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

        val comment: Comment = Comment(
            content = request.content,
            post = post,
            user = user
        )
        commentRepository.save(comment)
        return CommentResponse.fromEntity(comment)
    }

    override fun updateComment(commentId: Long, request: CommentUpdateRequest): CommentResponse {
        val comment: Comment =
            commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("comment", commentId)

        comment.updateContent(request)
        commentRepository.save(comment)
        return CommentResponse.fromEntity(comment)
    }

    override fun deleteComment(commentId: Long) {
        val comment: Comment =
            commentRepository.findByIdOrNull(commentId) ?: throw ModelNotFoundException("comment", commentId)

        comment.softDelete()
        commentRepository.save(comment)
    }
}