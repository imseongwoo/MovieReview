package org.teamsparta.moviereview.domain.post.service.v2.keyword

import org.springframework.cache.annotation.Cacheable
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.teamsparta.moviereview.domain.post.model.keyword.Keyword
import org.teamsparta.moviereview.domain.post.repository.v1.keyword.KeywordRepository
import java.time.LocalDateTime

@Service
class KeywordServiceImpl2(
    private val keywordRepository: KeywordRepository
) : KeywordService2 {

    @Transactional
    override fun saveKeyword(searchWord: String) {
        val searchKeyword = Keyword.of(searchWord)
        keywordRepository.save(searchKeyword)
    }

    @Cacheable("hotKeywordsLastHour", cacheManager = "caffeineCacheManager")
    override fun getHotKeywordsLastHour(): List<String> {
        val now = LocalDateTime.now()
        val from = now.minusHours(1)
        return keywordRepository.getHotKeywords(from, now)
    }

    @Cacheable("hotKeywordsLastDay", cacheManager = "caffeineCacheManager")
    override fun getHotKeywordsLastDay(): List<String> {
        val now = LocalDateTime.now()
        val from = now.minusDays(1)
        return keywordRepository.getHotKeywords(from, now)
    }

    @Scheduled(cron = "0 0 0 * * ?")
    override fun deleteOldKeywords() {
        val expiryDate = LocalDateTime.now().minusDays(3)
        keywordRepository.deleteKeywords(expiryDate)
    }
}