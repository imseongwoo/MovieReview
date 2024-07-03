package org.teamsparta.moviereview.domain.post.repository.v1.search

import org.springframework.data.jpa.repository.JpaRepository
import org.teamsparta.moviereview.domain.post.model.search.Keyword

interface KeywordRepository: JpaRepository<Keyword, Long> {
}