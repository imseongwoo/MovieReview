package org.teamsparta.moviereview.domain.post.controller.v2

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.teamsparta.moviereview.domain.post.dto.CreatePostRequest
import org.teamsparta.moviereview.domain.post.dto.PostResponse
import org.teamsparta.moviereview.domain.post.dto.PostResponseWithComments
import org.teamsparta.moviereview.domain.post.dto.UpdatePostRequest
import org.teamsparta.moviereview.domain.post.dto.report.ReportPostRequest
import org.teamsparta.moviereview.domain.post.service.v2.PostService2
import org.teamsparta.moviereview.infra.security.UserPrincipal

@RequestMapping("/api/v2/posts")
@RestController
class PostController2(
    private val postService2: PostService2
) {

    @GetMapping
    fun getPostList(
        @PageableDefault(size = 10) pageable: Pageable,
        @RequestParam category: String?
    ): ResponseEntity<Page<PostResponse>> {
        return ResponseEntity.ok(postService2.getPostList(pageable, category))
    }

    @GetMapping("{postId}")
    fun getPostById(@PathVariable postId:Long): ResponseEntity<PostResponseWithComments> {
        return ResponseEntity.ok(postService2.getPostById(postId))
    }

    @PostMapping
    fun createPost(
        @AuthenticationPrincipal principal: UserPrincipal,
        @RequestBody request: CreatePostRequest
    ): ResponseEntity<PostResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService2.createPost(principal, request))
    }

    @PutMapping("/{postId}")
    fun updatePost(
        @AuthenticationPrincipal principal: UserPrincipal,
        @PathVariable postId: Long,
        @RequestBody request: UpdatePostRequest
    ): ResponseEntity<Unit> {
        postService2.updatePost(principal, postId, request)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{postId}")
    fun deletePost(
        @AuthenticationPrincipal principal: UserPrincipal,
        @PathVariable postId: Long
    ): ResponseEntity<Unit> {
        postService2.deletePost(principal, postId)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/search")
    fun searchPost(
        pageable: Pageable,
        @RequestParam keyword: String,
    ): ResponseEntity<Page<PostResponse>> {
        return ResponseEntity.status(HttpStatus.OK).body(postService2.searchPost(pageable, keyword))
    }

    @PostMapping("/{postId}/thumbs-up")
    fun thumbsUpPost(
        @AuthenticationPrincipal principal: UserPrincipal,
        @PathVariable postId: Long
    ): ResponseEntity<Unit> {
        postService2.thumbsUpPost(principal, postId)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{postId}/thumbs-up")
    fun cancelThumbsUpPost(
        @AuthenticationPrincipal principal: UserPrincipal,
        @PathVariable postId: Long
    ): ResponseEntity<Unit> {
        postService2.cancelThumbsUpPost(principal, postId)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/{postId}/reports")
    fun reportPost(
        @AuthenticationPrincipal principal: UserPrincipal,
        @PathVariable postId: Long,
        @RequestBody request: ReportPostRequest
    ): ResponseEntity<Unit> {
        postService2.reportPost(principal, postId, request)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{postId}/reports/{reportId}")
    fun cancelReportPost(
        @AuthenticationPrincipal principal: UserPrincipal,
        @PathVariable postId: Long,
        @PathVariable reportId: Long
    ): ResponseEntity<Unit> {
        postService2.cancelReportPost(principal, reportId)
        return ResponseEntity.noContent().build()
    }


}