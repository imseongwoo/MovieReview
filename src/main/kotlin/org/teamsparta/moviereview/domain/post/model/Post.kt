package org.teamsparta.moviereview.domain.post.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.SQLRestriction
import org.teamsparta.moviereview.domain.common.time.BaseEntity
import org.teamsparta.moviereview.domain.users.model.Users

@Entity
@Table(name = "post")
@SQLRestriction("deleted_at is null")
class Post (
    @Column
    var title: String,

    @Column
    var content: String,

    @Enumerated(EnumType.STRING)
    var category: Category,

    @ManyToOne(fetch = FetchType.LAZY)
    var user: Users,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    companion object {
        fun of(title: String, content: String, category: String, user: Users): Post {
            val post = Post(
                title = title, content = content, category = Category.fromString(category), user = user
            )

            return post
        }
    }

    fun updatePost(title: String, content: String, category: String) {
        this.title = title
        this.content = content
        this.category = Category.fromString(category)
    }

    fun checkPermission(userId: Long, role: String): Boolean {
        return user.id == userId || role == "ROLE_ADMIN"
    }

}