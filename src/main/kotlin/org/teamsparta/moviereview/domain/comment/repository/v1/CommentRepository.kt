package org.teamsparta.moviereview.domain.comment.repository.v1

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.teamsparta.moviereview.domain.comment.model.Comment

@Repository
interface CommentRepository : JpaRepository<Comment, Long> {
    fun findAllByPostId(postId: Long): List<Comment>
}