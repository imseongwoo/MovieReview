package org.teamsparta.moviereview.domain.post.controller.v1

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.teamsparta.moviereview.domain.post.dto.*
import org.teamsparta.moviereview.domain.post.dto.report.ReportPostRequest
import org.teamsparta.moviereview.domain.post.service.v1.PostService
import org.teamsparta.moviereview.infra.security.UserPrincipal

@RequestMapping("/api/v1/posts")
@RestController
class PostController(
    private val postService: PostService
) {

    @GetMapping
    fun getPostList(
        @PageableDefault(size = 10) pageable: Pageable,
        @RequestParam category: String?
    ): ResponseEntity<Page<PostResponse>> {
        return ResponseEntity.ok(postService.getPostList(pageable, category))
    }

    @GetMapping("{postId}")
    fun getPostById(@PathVariable postId: Long): ResponseEntity<PostResponseWithComments> {
        return ResponseEntity.ok(postService.getPostById(postId))
    }

    @PostMapping
    fun createPost(
        @AuthenticationPrincipal principal: UserPrincipal,
        @RequestBody request: CreatePostRequest
    ): ResponseEntity<PostResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(principal, request))
    }

    @PutMapping("/{postId}")
    fun updatePost(
        @AuthenticationPrincipal principal: UserPrincipal,
        @PathVariable postId: Long,
        @RequestBody request: UpdatePostRequest
    ): ResponseEntity<Unit> {
        postService.updatePost(principal, postId, request)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{postId}")
    fun deletePost(
        @AuthenticationPrincipal principal: UserPrincipal,
        @PathVariable postId: Long
    ): ResponseEntity<Unit> {
        postService.deletePost(principal, postId)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/search")
    fun searchPost(
        pageable: Pageable,
        @RequestParam keyword: String,
    ): ResponseEntity<Page<PostResponseWithThumbsUpAndComments>> {
        return ResponseEntity.status(HttpStatus.OK).body(postService.searchPost(pageable, keyword))
    }

    @PostMapping("/{postId}/thumbs-up")
    fun thumbsUpPost(
        @AuthenticationPrincipal principal: UserPrincipal,
        @PathVariable postId: Long
    ): ResponseEntity<Unit> {
        postService.thumbsUpPost(principal, postId)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{postId}/thumbs-up")
    fun cancelThumbsUpPost(
        @AuthenticationPrincipal principal: UserPrincipal,
        @PathVariable postId: Long
    ): ResponseEntity<Unit> {
        postService.cancelThumbsUpPost(principal, postId)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/{postId}/reports")
    fun reportPost(
        @AuthenticationPrincipal principal: UserPrincipal,
        @PathVariable postId: Long,
        @RequestBody request: ReportPostRequest
    ): ResponseEntity<Unit> {
        postService.reportPost(principal, postId, request)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{postId}/reports/{reportId}")
    fun cancelReportPost(
        @AuthenticationPrincipal principal: UserPrincipal,
        @PathVariable postId: Long,
        @PathVariable reportId: Long
    ): ResponseEntity<Unit> {
        postService.cancelReportPost(principal, reportId)
        return ResponseEntity.noContent().build()
    }


}