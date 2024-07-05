package org.teamsparta.moviereview.domain.post.controller.v2.keyword

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.teamsparta.moviereview.domain.post.service.v2.keyword.KeywordService2

@RequestMapping("/api/v2/keywords")
@RestController
class KeywordController2(
    private val keywordService: KeywordService2
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