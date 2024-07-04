package org.teamsparta.moviereview.domain.comment.model

import jakarta.persistence.*
import org.teamsparta.moviereview.domain.comment.dto.CommentUpdateRequest
import org.teamsparta.moviereview.domain.common.time.BaseEntity
import org.teamsparta.moviereview.domain.post.model.Post
import org.teamsparta.moviereview.domain.users.model.UserRole
import org.teamsparta.moviereview.domain.users.model.Users
import java.time.LocalDateTime

@Entity
@Table(name = "comment")
class Comment(
    @Column
    var content: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    var post: Post,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: Users,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    fun updateContent(commentUpdateRequest: CommentUpdateRequest) {
        this.content = commentUpdateRequest.content
        this.updatedAt = LocalDateTime.now()
    }

    fun checkPermission(user: Users): Boolean {
        return this.id == user.id || user.role == UserRole.ADMIN
    }
}