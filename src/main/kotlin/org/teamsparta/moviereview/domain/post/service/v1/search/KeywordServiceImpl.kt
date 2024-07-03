package org.teamsparta.moviereview.domain.post.service.v1.search

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.teamsparta.moviereview.domain.post.model.search.Keyword
import org.teamsparta.moviereview.domain.post.repository.v1.search.KeywordRepository
import java.time.LocalDateTime

@Service
class KeywordServiceImpl(private val keywordRepository: KeywordRepository): KeywordService {

    @Transactional
    override fun saveKeyword(searchWord: String) {
        val searchKeyword = Keyword.of(searchWord)
        keywordRepository.save(searchKeyword)
    }

    override fun getHotKeywordsLastHour(): List<String> {
        val now = LocalDateTime.now()
        val from = now.minusHours(1)
        return keywordRepository.getHotKeywords(from, now)
    }

    override fun getHotKeywordsLastDay(): List<String> {
        val now = LocalDateTime.now()
        val from = now.minusDays(1)
        return keywordRepository.getHotKeywords(from, now)
    }
}