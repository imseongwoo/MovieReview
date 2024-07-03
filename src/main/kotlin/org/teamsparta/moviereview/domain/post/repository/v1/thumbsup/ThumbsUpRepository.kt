package org.teamsparta.moviereview.domain.post.repository.v1.thumbsup

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.teamsparta.moviereview.domain.post.model.thumbsup.ThumbsUp

@Repository
interface ThumbsUpRepository: JpaRepository<ThumbsUp, Long> {
    fun existsByPostIdAndUserId(postId: Long, userId: Long): Boolean
    fun findByPostIdAndUserId(postId: Long, userId: Long) : ThumbsUp?
}