package org.teamsparta.moviereview.domain.post.service.v2

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.teamsparta.moviereview.domain.post.dto.PostResponse
import org.teamsparta.moviereview.domain.post.model.keyword.Keyword
import org.teamsparta.moviereview.domain.post.repository.v1.keyword.KeywordRepository

@Service
class SearchPostService2(
    private val postService2 : PostService2,
    private val keywordRepository: KeywordRepository
) {
    fun searchPost(pageable: Pageable, keyword: String?): Page<PostResponse> {
        keyword?.let { saveSearchKeyword(keyword) }
        return postService2.searchPostByKeyword(pageable, keyword)
    }

    private fun saveSearchKeyword(keyword: String) {
        val saveKeyword = Keyword.of(keyword)
        keywordRepository.save(saveKeyword)
    }
}