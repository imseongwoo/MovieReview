package org.teamsparta.moviereview.domain.post.repository.v1.keyword

import org.springframework.data.jpa.repository.JpaRepository
import org.teamsparta.moviereview.domain.post.model.keyword.Keyword

interface KeywordRepository: JpaRepository<Keyword, Long>, CustomKeywordRepository {
}