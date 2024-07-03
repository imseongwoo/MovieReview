package org.teamsparta.moviereview.domain.post.service.v1.search

import org.teamsparta.moviereview.domain.post.dto.PostResponse

interface KeywordService {
    fun saveKeyword(keyword: String)
}