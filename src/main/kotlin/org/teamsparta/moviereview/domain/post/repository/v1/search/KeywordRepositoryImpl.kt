package org.teamsparta.moviereview.domain.post.repository.v1.search

import org.teamsparta.moviereview.domain.post.model.search.QKeyword
import org.teamsparta.moviereview.infra.querydsl.QueryDslSupport
import java.time.LocalDateTime

class KeywordRepositoryImpl : CustomKeywordRepository, QueryDslSupport() {
    override fun getHotKeywords(from: LocalDateTime, to: LocalDateTime): List<String> {
        val keyword = QKeyword.keyword

        return queryFactory
            .select(keyword.keyword)
            .from(keyword)
            .where(keyword.createdAt.between(from, to))
            .groupBy(keyword.keyword)
            .orderBy(keyword.keyword.count().desc())
            .limit(10)
            .fetch()
    }
}