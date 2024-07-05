package org.teamsparta.moviereview.domain.post.service.v3.keyword

interface KeywordService3 {
    fun saveKeyword(searchWord: String)
    fun getHotKeywordsLastHour(): List<String>
    fun getHotKeywordsLastDay(): List<String>
    fun deleteOldKeywords()
}