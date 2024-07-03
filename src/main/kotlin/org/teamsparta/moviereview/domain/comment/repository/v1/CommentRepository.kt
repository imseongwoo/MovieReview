package org.teamsparta.moviereview.domain.comment.repository.v1

import org.springframework.data.jpa.repository.JpaRepository
import org.teamsparta.moviereview.domain.comment.model.Comment

interface CommentRepository : JpaRepository<Comment, Long> {
}