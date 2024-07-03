package org.teamsparta.moviereview.domain.post.service.v1

import org.springframework.stereotype.Service
import org.teamsparta.moviereview.domain.post.dto.CreatePostRequest
import org.teamsparta.moviereview.domain.post.dto.PostResponse
import org.teamsparta.moviereview.domain.post.dto.ReportPostRequest
import org.teamsparta.moviereview.domain.post.dto.UpdatePostRequest

@Service
class PostServiceImpl: PostService {
    override fun getPostList(): List<PostResponse> {
        //deletedAt이 null인 게시글 전체 조회
        TODO("Not yet implemented")
    }

    override fun getPostById(postId: Long): PostResponse {
        //deletedAt이 null인 게시글 전체 조회
        TODO("Not yet implemented")
    }

    override fun createPost(request: CreatePostRequest): PostResponse {
        // 로그인한 유저인지 확인

        // Request를 엔티티로 변환한 뒤 저장
        TODO("Not yet implemented")
    }

    override fun updatePost(postId: Long, request: UpdatePostRequest): PostResponse {
        // Post 엔티티 조회

        // 본인이 작성한 포스트 인지 확인

        // 포스트 업데이트

        TODO("Not yet implemented")
    }

    override fun deletePost(postId: Long) {
        // 포스트 조회

        // 본인이 작성한 포스트인지 확인

        // 포스트 soft delete
        TODO("Not yet implemented")
    }

    override fun searchPost(title: String?, nickname: String?, content: String?): List<PostResponse> {
        // 검색 기준 바탕으로 검색

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