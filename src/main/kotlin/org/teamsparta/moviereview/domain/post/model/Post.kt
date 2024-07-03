package org.teamsparta.moviereview.domain.post.model

import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import org.teamsparta.moviereview.domain.users.model.Users

class Post (
    @Column
    var title: String,

    @Column
    var content: String,

    @Enumerated(EnumType.STRING)
    var category: Category,

    @ManyToOne(fetch = FetchType.LAZY)
    var user: Users,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}