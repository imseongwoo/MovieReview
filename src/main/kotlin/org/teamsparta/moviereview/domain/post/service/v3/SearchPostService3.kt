package org.teamsparta.moviereview.domain.post.service.v3

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.teamsparta.moviereview.domain.post.dto.PostResponse
import org.teamsparta.moviereview.domain.post.model.keyword.Keyword
import org.teamsparta.moviereview.domain.post.repository.v1.keyword.KeywordRepository

@Service
class SearchPostService3(
    private val postService3 : PostService3,
    private val keywordRepository: KeywordRepository
) {
    fun searchPost(pageable: Pageable, keyword: String?): Page<PostResponse> {
        keyword?.let { saveSearchKeyword(keyword) }
        return postService3.searchPostByKeyword(pageable, keyword)
    }

    private fun saveSearchKeyword(keyword: String) {
        val saveKeyword = Keyword.of(keyword)
        keywordRepository.save(saveKeyword)
    }
}