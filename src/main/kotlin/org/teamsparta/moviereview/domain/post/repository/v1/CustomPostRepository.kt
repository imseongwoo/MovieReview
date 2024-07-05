package org.teamsparta.moviereview.domain.post.repository.v1

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.teamsparta.moviereview.domain.post.dto.PostResponse
import org.teamsparta.moviereview.domain.post.model.Post

interface CustomPostRepository {
    fun findAllByPageableAndCategory(pageable: Pageable, category: String?): Triple<Long, List<Post>, List<Long>>
    fun searchPostByPageableAndKeyword(pageable:Pageable, keyword:String?): Page<PostResponse>
}