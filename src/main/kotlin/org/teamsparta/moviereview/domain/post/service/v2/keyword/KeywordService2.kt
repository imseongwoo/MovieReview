package org.teamsparta.moviereview.domain.post.service.v2.keyword

interface KeywordService2 {
    fun saveKeyword(searchWord: String)
    fun getHotKeywordsLastHour(): List<String>
    fun getHotKeywordsLastDay(): List<String>
    fun deleteOldKeywords()
}