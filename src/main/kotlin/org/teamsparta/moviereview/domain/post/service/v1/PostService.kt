package org.teamsparta.moviereview.domain.post.service.v1

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.teamsparta.moviereview.domain.post.dto.*
import org.teamsparta.moviereview.domain.post.dto.report.ReportPostRequest
import org.teamsparta.moviereview.infra.security.UserPrincipal

interface PostService {
    fun getPostList(pageable: Pageable, category: String?): Page<PostResponse>
    fun getPostById(postId: Long): PostResponseWithComments
    fun createPost(principal: UserPrincipal, request: CreatePostRequest): PostResponse
    fun updatePost(principal: UserPrincipal, postId: Long, request: UpdatePostRequest)
    fun deletePost(principal: UserPrincipal, postId: Long)
    fun searchPost(pageable: Pageable, keyword: String?): Page<PostResponse>
    fun thumbsUpPost(principal: UserPrincipal, postId: Long)
    fun cancelThumbsUpPost(principal: UserPrincipal, postId: Long)
    fun reportPost(principal: UserPrincipal, postId: Long, request: ReportPostRequest)
    fun cancelReportPost(principal: UserPrincipal, reportId: Long)
}