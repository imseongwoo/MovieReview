package org.teamsparta.moviereview.domain.post.repository.v1

import com.querydsl.core.BooleanBuilder
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import org.teamsparta.moviereview.domain.comment.model.QComment
import org.teamsparta.moviereview.domain.post.dto.PostResponseWithThumbsUpAndComments
import org.teamsparta.moviereview.domain.post.model.QPost
import org.teamsparta.moviereview.domain.post.model.search.Keyword
import org.teamsparta.moviereview.domain.post.model.thumbsup.QThumbsUp
import org.teamsparta.moviereview.domain.post.repository.v1.search.KeywordRepository
import org.teamsparta.moviereview.domain.users.model.QUsers
import org.teamsparta.moviereview.infra.querydsl.QueryDslSupport
import java.time.LocalDateTime

@Repository
class PostRepositoryImpl(private val keywordRepository: KeywordRepository) : CustomPostRepository, QueryDslSupport() {

    private val post = QPost.post
    private val comment = QComment.comment
    private val user = QUsers.users
    private val thumbsUp = QThumbsUp.thumbsUp

    override fun findAllByPageableAndKeyword(
        pageable: Pageable,
        keyword: String?
    ): Page<PostResponseWithThumbsUpAndComments> {

        val whereClause = BooleanBuilder()

        keyword?.let {
            whereClause.and(
                post.title.containsIgnoreCase(it)
                    .or(post.content.containsIgnoreCase(it))
            )
            saveSearchKeyword(it)
        }

        val totalCount = queryFactory.selectFrom(post)
            .where(whereClause)
            .fetchCount()

        val posts = queryFactory.selectFrom(post)
            .leftJoin(post.user, user).fetchJoin()
            .where(whereClause)
            .orderBy(post.createdAt.desc())
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val postIds = posts.map { it.id }

        val thumbsUpCounts = queryFactory
            .select(thumbsUp.postId, thumbsUp.count())
            .from(thumbsUp)
            .where(thumbsUp.postId.`in`(postIds))
            .groupBy(thumbsUp.postId)
            .fetch()
            .associate { it[thumbsUp.postId] to it[thumbsUp.count()] }

        val commentsMap = queryFactory
            .select(comment)
            .where(comment.post.id.`in`(postIds))
            .fetch()
            .groupBy { it.post.id }

        val postResponseWithThumbsUpAndComments = posts.map { post ->
            PostResponseWithThumbsUpAndComments(
                post = post,
                thumbsUpCount = thumbsUpCounts[post.id] ?: -1L,
                comments = commentsMap[post.id] ?: emptyList()
            )
        }

        return PageImpl(postResponseWithThumbsUpAndComments, pageable, totalCount)
    }

    fun saveSearchKeyword(keyword: String) {
        val keyword = Keyword(keyword = keyword, createdAt = LocalDateTime.now())
        keywordRepository.save(keyword)
    }
}