package org.teamsparta.moviereview.domain.post.model.thumbsup

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name="thumbs_up")
class ThumbsUp(
    @Column(name = "post_id", nullable = false)
    val postId: Long,

    @Column(name = "user_id", nullable = false)
    val userId: Long
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}