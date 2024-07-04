package org.teamsparta.moviereview.domain.post.repository.v1

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.teamsparta.moviereview.domain.post.dto.PostResponseWithThumbsUpAndComments

interface CustomPostRepository {
    fun findAllByPageableAndKeyword(pageable: Pageable, keyword: String?): Page<PostResponseWithThumbsUpAndComments>
}