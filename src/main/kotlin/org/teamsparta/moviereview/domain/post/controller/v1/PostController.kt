package org.teamsparta.moviereview.domain.post.controller.v1

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
import org.teamsparta.moviereview.domain.post.dto.CreatePostRequest
import org.teamsparta.moviereview.domain.post.dto.PostResponse
import org.teamsparta.moviereview.domain.post.dto.report.ReportPostRequest
import org.teamsparta.moviereview.domain.post.dto.UpdatePostRequest
import org.teamsparta.moviereview.domain.post.service.v1.PostService
import org.teamsparta.moviereview.infra.security.UserPrincipal

@RequestMapping("/api/v1/posts")
@RestController
class PostController(
    private val postService: PostService
) {

    @GetMapping
    fun getPostList(@RequestParam category: String?): ResponseEntity<List<PostResponse>> {
        return ResponseEntity.ok(postService.getPostList(category))
    }

    @GetMapping("{postId}")
    fun getPostById(@PathVariable postId:Long): ResponseEntity<PostResponse> {
        return ResponseEntity.ok(postService.getPostById(postId))
    }

    @PostMapping
    fun createCourse(
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
    ): ResponseEntity<PostResponse> {
        return ResponseEntity.ok(postService.updatePost(principal, postId, request))
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
        @RequestParam keyword: String,
        ) : ResponseEntity<List<PostResponse>> {
        return ResponseEntity.ok(postService.searchPost(keyword))
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
        postService.reportPost(principal.id, postId, request)
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