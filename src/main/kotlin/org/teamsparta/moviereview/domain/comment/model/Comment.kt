package org.teamsparta.moviereview.domain.comment.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.SQLRestriction
import org.teamsparta.moviereview.domain.comment.dto.CommentUpdateRequest
import org.teamsparta.moviereview.domain.common.time.BaseEntity
import org.teamsparta.moviereview.domain.post.model.Post
import org.teamsparta.moviereview.domain.users.model.UserRole
import org.teamsparta.moviereview.domain.users.model.Users

@Entity
@Table(name = "comment")
@SQLRestriction("deleted_at is null")
class Comment(
    @Column
    var content: String,

    @ManyToOne(fetch = FetchType.LAZY)
    var post: Post,

    @ManyToOne(fetch = FetchType.LAZY)
    var user: Users,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    fun updateContent(commentUpdateRequest: CommentUpdateRequest) {
        this.content = commentUpdateRequest.content
    }

    fun checkPermission(user: Users): Boolean {
        return this.id == user.id || user.role == UserRole.ADMIN
    }
}