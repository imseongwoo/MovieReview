package org.teamsparta.moviereview.domain.post.controller.v1.search

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.teamsparta.moviereview.domain.post.service.v1.search.KeywordService

@RequestMapping("/api/v1/keywords")
@RestController
class KeywordController(
    private val keywordService: KeywordService
) {
    @GetMapping("/last-hour")
    fun getHotKeywordsLastHour(): List<String> {
        return keywordService.getHotKeywordsLastHour()
    }

    @GetMapping("/last-day")
    fun getHotKeywordsLastDay(): List<String> {
        return keywordService.getHotKeywordsLastDay()
    }
}