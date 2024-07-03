package org.teamsparta.moviereview.domain.post.repository.v1.search

import org.teamsparta.moviereview.domain.post.model.search.QKeyword
import org.teamsparta.moviereview.infra.querydsl.QueryDslSupport
import java.time.LocalDateTime

class KeywordRepositoryImpl : CustomKeywordRepository, QueryDslSupport() {
    override fun getHotKeywords(from: LocalDateTime, to: LocalDateTime): List<String> {
        val keyword = QKeyword.keyword

        return queryFactory
            .select(keyword.searchWord)
            .from(keyword)
            .where(keyword.createdAt.between(from, to))
            .groupBy(keyword.searchWord)
            .orderBy(keyword.searchWord.count().desc())
            .limit(10)
            .fetch()
    }

    override fun deleteKeywords(expiryDate: LocalDateTime) {
        val keyword = QKeyword.keyword

        queryFactory.delete(keyword)
            .where(keyword.createdAt.before(expiryDate))
            .execute()
    }
}