package org.teamsparta.moviereview.domain.post.controller.v3.keyword

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.teamsparta.moviereview.domain.post.service.v3.keyword.KeywordService3

@RequestMapping("/api/v3/keywords")
@RestController
class KeywordController3(
    private val keywordService: KeywordService3
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