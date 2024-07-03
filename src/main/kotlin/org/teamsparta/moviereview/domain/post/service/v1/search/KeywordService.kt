package org.teamsparta.moviereview.domain.post.service.v1.search

interface KeywordService {
    fun saveKeyword(keyword: String)
    fun getHotKeywordsLastHour(): List<String>
    fun getHotKeywordsLastDay(): List<String>
}