package org.teamsparta.moviereview.domain.post.service.v1

import org.teamsparta.moviereview.domain.post.dto.CreatePostRequest
import org.teamsparta.moviereview.domain.post.dto.PostResponse
import org.teamsparta.moviereview.domain.post.dto.ReportPostRequest
import org.teamsparta.moviereview.domain.post.dto.UpdatePostRequest

interface PostService {
    fun getPostList(category: String): List<PostResponse>
    fun getPostById(postId: Long): PostResponse
    fun createPost(request: CreatePostRequest): PostResponse
    fun updatePost(postId: Long, request: UpdatePostRequest): PostResponse
    fun deletePost(postId: Long)
    fun searchPost(keyword: String): List<PostResponse>
    fun thumbsUpPost(postId: Long)
    fun cancelThumbsUpPost(postId: Long)
    fun reportPost(postId: Long, request: ReportPostRequest)
    fun cancelReportPost(reportId: Long)
}