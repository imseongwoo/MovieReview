package org.teamsparta.moviereview.domain.post.service.v1

import org.teamsparta.moviereview.domain.post.dto.CreatePostRequest
import org.teamsparta.moviereview.domain.post.dto.PostResponse
import org.teamsparta.moviereview.domain.post.dto.ReportPostRequest
import org.teamsparta.moviereview.domain.post.dto.UpdatePostRequest
import org.teamsparta.moviereview.infra.security.UserPrincipal

interface PostService {
    fun getPostList(category: String?): List<PostResponse>
    fun getPostById(postId: Long): PostResponse
    fun createPost(principal: UserPrincipal, request: CreatePostRequest): PostResponse
    fun updatePost(principal: UserPrincipal, postId: Long, request: UpdatePostRequest): PostResponse
    fun deletePost(principal: UserPrincipal, postId: Long)
    fun searchPost(keyword: String): List<PostResponse>
    fun thumbsUpPost(principal: UserPrincipal, postId: Long)
    fun cancelThumbsUpPost(principal: UserPrincipal, postId: Long)
    fun reportPost(postId: Long, request: ReportPostRequest)
    fun cancelReportPost(reportId: Long)
}