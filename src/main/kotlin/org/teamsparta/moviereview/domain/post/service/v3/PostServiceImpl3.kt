package org.teamsparta.moviereview.domain.post.service.v3

import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
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
import org.teamsparta.moviereview.domain.post.model.keyword.Keyword
import org.teamsparta.moviereview.domain.post.model.report.Report
import org.teamsparta.moviereview.domain.post.model.thumbsup.ThumbsUp
import org.teamsparta.moviereview.domain.post.repository.v1.PostRepository
import org.teamsparta.moviereview.domain.post.repository.v1.keyword.KeywordRepository
import org.teamsparta.moviereview.domain.post.repository.v1.report.ReportRepository
import org.teamsparta.moviereview.domain.post.repository.v1.thumbsup.ThumbsUpRepository
import org.teamsparta.moviereview.domain.users.repository.v1.UserRepository
import org.teamsparta.moviereview.infra.security.UserPrincipal

@Service
class PostServiceImpl3(
    private val postRepository: PostRepository,
    private val thumbsUpRepository: ThumbsUpRepository,
    private val userRepository: UserRepository,
    private val commentRepository: CommentRepository,
    private val reportRepository: ReportRepository,
    private val keywordRepository: KeywordRepository
) : PostService3 {
    override fun getPostList(pageable: Pageable, category: String?): Page<PostResponse> {
        val (totalCount, post, thumbsUpCount) = postRepository.findAllByPageableAndCategory(pageable, category)

        val postResponseList = post.zip(thumbsUpCount) { a, b -> PostResponse.from(a, b) }

        return PageImpl(postResponseList, pageable, totalCount)
    }

    @Transactional(readOnly = true)

    override fun getPostById(postId: Long): PostResponseWithComments {
        val post = postRepository.findByIdOrNull(postId) ?: throw ModelNotFoundException("Post", postId)

        val commentList = commentRepository.findAllByPostId(postId)
            .map { CommentResponse.fromEntity(it) }

        return PostResponseWithComments.from(post, thumbsUpRepository.thumbsUpCount(postId), commentList)
    }

    @CacheEvict("searchPostRedis", cacheManager = "redisCacheManager", allEntries = true)
    override fun createPost(principal: UserPrincipal, request: CreatePostRequest): PostResponse {
        val user = userRepository.findByIdOrNull(principal.id) ?: throw ModelNotFoundException("User", principal.id)

        return postRepository.save(Post.of(request.title, request.content, request.category, user))
            .let { PostResponse.from(it, 0) }
    }

    @Transactional
    override fun updatePost(principal: UserPrincipal, postId: Long, request: UpdatePostRequest) {
        postRepository.findByIdOrNull(postId)
            ?.also { checkPermission(it, principal) }
            ?.apply { this.updatePost(request.title, request.content, request.category) }
            ?: throw ModelNotFoundException("Post", postId)
    }

    @Transactional
    override fun deletePost(principal: UserPrincipal, postId: Long) {

        postRepository.findByIdOrNull(postId)
            ?.also { checkPermission(it, principal) }
            ?.apply { this.softDelete() }
            ?: throw ModelNotFoundException("Post", postId)

        commentRepository.findAllByPostId(postId)
            .map { it.softDelete() }

        thumbsUpRepository.deleteAllByPostId(postId)
    }

    @Cacheable("searchPostRedis", cacheManager = "redisCacheManager")
    override fun searchPost(pageable: Pageable, keyword: String?): Page<PostResponse> {
        keyword?.let { saveSearchKeyword(keyword) }
        return postRepository.searchPostByPageableAndKeyword(pageable, keyword)
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
            ?.also { checkReportPermission(it, principal) }
            ?.let { reportRepository.delete(it) }
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

    private fun saveSearchKeyword(keyword: String) {
        val saveKeyword = Keyword.of(keyword)
        keywordRepository.save(saveKeyword)
    }
}