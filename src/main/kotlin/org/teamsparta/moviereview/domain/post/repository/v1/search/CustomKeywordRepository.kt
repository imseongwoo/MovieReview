package org.teamsparta.moviereview.domain.post.repository.v1.search

import java.time.LocalDateTime

interface CustomKeywordRepository {
    fun getHotKeywords(from: LocalDateTime, to: LocalDateTime): List<String>
    fun deleteKeywords(expiryDate: LocalDateTime)
}