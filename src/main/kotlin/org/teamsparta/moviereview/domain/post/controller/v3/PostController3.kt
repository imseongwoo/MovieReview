package org.teamsparta.moviereview.domain.post.controller.v3

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
import org.teamsparta.moviereview.domain.post.service.v2.SearchPostService2
import org.teamsparta.moviereview.domain.post.service.v3.PostService3
import org.teamsparta.moviereview.domain.post.service.v3.SearchPostService3
import org.teamsparta.moviereview.infra.security.UserPrincipal

@RequestMapping("/api/v3/posts")
@RestController
class PostController3(
    private val postService3: PostService3,
    private val searchPostService3 : SearchPostService3
) {

    @GetMapping
    fun getPostList(
        @PageableDefault(size = 10) pageable: Pageable,
        @RequestParam category: String?
    ): ResponseEntity<Page<PostResponse>> {
        return ResponseEntity.ok(postService3.getPostList(pageable, category))
    }

    @GetMapping("{postId}")
    fun getPostById(@PathVariable postId: Long): ResponseEntity<PostResponseWithComments> {
        return ResponseEntity.ok(postService3.getPostById(postId))
    }

    @PostMapping
    fun createPost(
        @AuthenticationPrincipal principal: UserPrincipal,
        @RequestBody request: CreatePostRequest
    ): ResponseEntity<PostResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService3.createPost(principal, request))
    }

    @PutMapping("/{postId}")
    fun updatePost(
        @AuthenticationPrincipal principal: UserPrincipal,
        @PathVariable postId: Long,
        @RequestBody request: UpdatePostRequest
    ): ResponseEntity<Unit> {
        postService3.updatePost(principal, postId, request)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{postId}")
    fun deletePost(
        @AuthenticationPrincipal principal: UserPrincipal,
        @PathVariable postId: Long
    ): ResponseEntity<Unit> {
        postService3.deletePost(principal, postId)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/search")
    fun searchPost(
        pageable: Pageable,
        @RequestParam keyword: String,
    ): ResponseEntity<Page<PostResponse>> {
        return ResponseEntity.status(HttpStatus.OK).body(searchPostService3.searchPost(pageable, keyword))
    }

    @PostMapping("/{postId}/thumbs-up")
    fun thumbsUpPost(
        @AuthenticationPrincipal principal: UserPrincipal,
        @PathVariable postId: Long
    ): ResponseEntity<Unit> {
        postService3.thumbsUpPost(principal, postId)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{postId}/thumbs-up")
    fun cancelThumbsUpPost(
        @AuthenticationPrincipal principal: UserPrincipal,
        @PathVariable postId: Long
    ): ResponseEntity<Unit> {
        postService3.cancelThumbsUpPost(principal, postId)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/{postId}/reports")
    fun reportPost(
        @AuthenticationPrincipal principal: UserPrincipal,
        @PathVariable postId: Long,
        @RequestBody request: ReportPostRequest
    ): ResponseEntity<Unit> {
        postService3.reportPost(principal, postId, request)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{postId}/reports/{reportId}")
    fun cancelReportPost(
        @AuthenticationPrincipal principal: UserPrincipal,
        @PathVariable postId: Long,
        @PathVariable reportId: Long
    ): ResponseEntity<Unit> {
        postService3.cancelReportPost(principal, reportId)
        return ResponseEntity.noContent().build()
    }


}