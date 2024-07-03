package org.teamsparta.moviereview.domain.post.service.v1.search

import org.springframework.stereotype.Service
import org.teamsparta.moviereview.domain.post.model.search.Keyword
import org.teamsparta.moviereview.domain.post.repository.v1.search.KeywordRepository

@Service
class KeywordServiceImpl(private val keywordRepository: KeywordRepository): KeywordService {
    override fun saveKeyword(keyword: String) {
        val searchWord = Keyword.of(keyword)
        keywordRepository.save(searchWord)
    }
}