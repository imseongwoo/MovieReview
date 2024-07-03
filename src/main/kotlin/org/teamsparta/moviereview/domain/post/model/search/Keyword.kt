package org.teamsparta.moviereview.domain.post.model.search

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
class Keyword(
    val keyword: String,
    val createdAt: LocalDateTime
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    companion object {
        fun of(keyword: String): Keyword {
            val timestamp = LocalDateTime.now()
            return Keyword(
                keyword = keyword,
                createdAt = timestamp
            )
        }
    }
}