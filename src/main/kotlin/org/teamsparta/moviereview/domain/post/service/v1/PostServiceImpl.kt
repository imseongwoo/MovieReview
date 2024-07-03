package org.teamsparta.moviereview.domain.post.service.v1

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.teamsparta.moviereview.domain.common.exception.ModelNotFoundException
import org.teamsparta.moviereview.domain.post.dto.CreatePostRequest
import org.teamsparta.moviereview.domain.post.dto.PostResponse
import org.teamsparta.moviereview.domain.post.dto.ReportPostRequest
import org.teamsparta.moviereview.domain.post.dto.UpdatePostRequest
import org.teamsparta.moviereview.domain.post.model.Category
import org.teamsparta.moviereview.domain.post.model.Post
import org.teamsparta.moviereview.domain.post.repository.v1.PostRepository
import org.teamsparta.moviereview.domain.users.model.Users

@Service
class PostServiceImpl(
    private val postRepository: PostRepository
): PostService {
    override fun getPostList(category: String): List<PostResponse> {
        // 페이지네이션 적용 예정
        return postRepository.findAllByCategoryOrderByCreatedAtDesc(Category.fromString(category))
            .map { PostResponse.from(it) }
    }

    override fun getPostById(postId: Long): PostResponse {
        return postRepository.findByIdOrNull(postId)
            ?. let { PostResponse.from(it) }
            ?: throw ModelNotFoundException("Post", postId)
    }

    override fun createPost(request: CreatePostRequest): PostResponse {
        // 로그인한 유저인지 확인, 나중에 수정
        val user = Users()

        return postRepository.save(Post.of(request.title, request.content, request.category, user))
            .let {PostResponse.from(it) }
    }

    @Transactional
    override fun updatePost(postId: Long, request: UpdatePostRequest): PostResponse {
        // 본인이 작성한 포스트 인지 확인, 나중에 추가

        return postRepository.findByIdOrNull(postId)
            ?.apply { this.updatePost(request.title, request.content, request.category) }
            ?.let { PostResponse.from(it) }
            ?: throw ModelNotFoundException("Post", postId)
    }

    @Transactional
    override fun deletePost(postId: Long) {
        // 본인이 작성한 포스트인지 확인, 나중에 추가
        postRepository.findByIdOrNull(postId)
            ?.apply { this.softDelete() }
            ?: throw ModelNotFoundException("Post", postId)
    }

    override fun searchPost(keyword: String): List<PostResponse> {
        // 검색 기준 바탕으로 검색
        // 페이지네이션 적용 예정

        // 검색어 저장
        TODO("Not yet implemented")
    }

    override fun thumbsUpPost(postId: Long) {
        // 로그인된 유저인지 확인

        // 좋아요 누른적 있는지 확인

        // 좋아요 테이블에 저장
        TODO("Not yet implemented")
    }

    override fun cancelThumbsUpPost(postId: Long) {
        // 로그인된 유저인지 확인

        // 좋아요 누른적 있는지 확인

        // 좋아요 테이블에서 삭제
        TODO("Not yet implemented")
    }

    override fun reportPost(postId: Long, request: ReportPostRequest) {
        // 포스트 조회

        // Request를 엔티티로 변경하여 저장

        TODO("Not yet implemented")
    }

    override fun cancelReportPost(reportId: Long) {
        // report 조회

        // 본인이 작성한 신고인지 확인

        // report 삭제
        TODO("Not yet implemented")
    }
}