package org.teamsparta.moviereview.domain.post.model

enum class Category {
    REVIEW, INFO, ETC;

    companion object {
        fun fromString(category: String): Category {
            return try {
                valueOf(category.uppercase())
            } catch (e: IllegalArgumentException) {
                throw IllegalArgumentException("Invalid category : $category")
            }
        }
    }
}