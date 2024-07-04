package org.teamsparta.moviereview.domain.post.service.v1

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.teamsparta.moviereview.domain.comment.dto.CommentResponse
import org.teamsparta.moviereview.domain.comment.repository.v1.CommentRepository
import org.teamsparta.moviereview.domain.common.exception.AccessDeniedException
import org.teamsparta.moviereview.domain.common.exception.ModelNotFoundException
import org.teamsparta.moviereview.domain.post.dto.CreatePostRequest
import org.teamsparta.moviereview.domain.post.dto.PostResponse
import org.teamsparta.moviereview.domain.post.dto.PostResponseWithComments
import org.teamsparta.moviereview.domain.post.dto.UpdatePostRequest
import org.teamsparta.moviereview.domain.post.dto.report.ReportPostRequest
import org.teamsparta.moviereview.domain.post.model.Post
import org.teamsparta.moviereview.domain.post.model.report.Report
import org.teamsparta.moviereview.domain.post.model.thumbsup.ThumbsUp
import org.teamsparta.moviereview.domain.post.repository.v1.PostRepository
import org.teamsparta.moviereview.domain.post.repository.v1.report.ReportRepository
import org.teamsparta.moviereview.domain.post.repository.v1.thumbsup.ThumbsUpRepository
import org.teamsparta.moviereview.domain.users.repository.v1.UserRepository
import org.teamsparta.moviereview.infra.security.UserPrincipal

@Service
class PostServiceImpl(
    private val postRepository: PostRepository,
    private val thumbsUpRepository: ThumbsUpRepository,
    private val userRepository: UserRepository,
    private val commentRepository: CommentRepository,
    private val reportRepository: ReportRepository
): PostService {
    override fun getPostList(pageable: Pageable, category: String?): Page<PostResponse> {
        // 좋아요 눌렀는지 여부(?)

        val (totalCount, post, thumbsUpCount) = postRepository.findAllByPageableAndCategory(pageable, category)

        val postResponseList = post.zip(thumbsUpCount) { a, b -> PostResponse.from(a,b) }

        return PageImpl(postResponseList, pageable, totalCount)
    }

    override fun getPostById(postId: Long): PostResponseWithComments {
        // 좋아요 눌렀는지 여부(?)
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)

        val commentList = commentRepository.findAllByPostId(postId)
            .map { CommentResponse.fromEntity(it) }

        return PostResponseWithComments.from(post, thumbsUpRepository.thumbsUpCount(postId), commentList)
    }

    override fun createPost(principal: UserPrincipal, request: CreatePostRequest): PostResponse {
        val user = userRepository.findByIdOrNull(principal.id) ?: throw ModelNotFoundException("User", principal.id)

        return postRepository.save(Post.of(request.title, request.content, request.category, user))
            .let { PostResponse.from(it, 0) }
    }

    @Transactional
    override fun updatePost(principal: UserPrincipal, postId: Long, request: UpdatePostRequest) {
        postRepository.findByIdOrNull(postId)
            ?. also { checkPermission(it, principal) }
            ?. apply { this.updatePost(request.title, request.content, request.category) }
            ?: throw ModelNotFoundException("Post", postId)
    }

    @Transactional
    override fun deletePost(principal: UserPrincipal, postId: Long) {
        // 포스트를 소프트 딜리트 할 때, 댓글과 좋아요는 어떻게 처리할 것인가?
        postRepository.findByIdOrNull(postId)
            ?. also { checkPermission(it, principal) }
            ?. apply { this.softDelete() }
            ?: throw ModelNotFoundException("Post", postId)
    }

    override fun searchPost(keyword: String): List<PostResponse> {
        // 검색 기준 바탕으로 검색
        // 페이지네이션 적용 예정

        // 검색어 저장
        TODO("Not yet implemented")
    }

    override fun thumbsUpPost(principal: UserPrincipal, postId: Long) {
        if (!isPostExists(postId)) throw ModelNotFoundException("Post", postId)

        if (thumbsUpRepository.existsByPostIdAndUserId(postId, principal.id))
            throw IllegalArgumentException("You've already given a thumbs up to this post")

        thumbsUpRepository.save(ThumbsUp(postId, principal.id))
    }

    override fun cancelThumbsUpPost(principal: UserPrincipal, postId: Long) {
        if (!isPostExists(postId)) throw ModelNotFoundException("Post", postId)

        val thumbsUp = thumbsUpRepository.findByPostIdAndUserId(postId, principal.id)
            ?: throw IllegalArgumentException("You've not given a thumbs up to this post, so it can't be canceled")

        thumbsUpRepository.delete(thumbsUp)
    }

    override fun reportPost(principal: UserPrincipal, postId: Long, request: ReportPostRequest) {
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)
        val (reportType, description) = request
        val user = userRepository.findByIdOrNull(principal.id) ?: throw ModelNotFoundException("User", principal.id)

        reportRepository.save(Report.of(reportType, description, user, post))
    }

    override fun cancelReportPost(principal: UserPrincipal, reportId: Long) {
        reportRepository.findByIdOrNull(reportId)
            ?. also { checkReportPermission(it, principal) }
            ?. let { reportRepository.delete(it) }
            ?: throw ModelNotFoundException("Report", reportId)
    }

    private fun checkPermission(post: Post, principal: UserPrincipal) {
        check(
            post.checkPermission(
                principal.id,
                principal.authorities.first().authority
            )
        ) { throw AccessDeniedException("You do not own this post") }
    }

    private fun checkReportPermission(report: Report, principal: UserPrincipal) {
        check(
            report.checkReportPermission(
                principal.id,
                principal.authorities.first().authority
            )
        ) { throw AccessDeniedException("You do not own this report") }
    }

    private fun isPostExists(postId: Long): Boolean {
        return postRepository.existsById(postId)
    }

    private fun ThumbsUpRepository.thumbsUpCount(postId: Long): Long {
        return thumbsUpRepository.countByPostId(postId)
    }
}