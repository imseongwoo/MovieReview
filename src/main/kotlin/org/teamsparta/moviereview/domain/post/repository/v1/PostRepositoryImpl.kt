package org.teamsparta.moviereview.domain.post.repository.v1

import com.querydsl.core.BooleanBuilder
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import org.teamsparta.moviereview.domain.comment.model.QComment
import org.teamsparta.moviereview.domain.post.dto.PostResponse
import org.teamsparta.moviereview.domain.post.model.Category
import org.teamsparta.moviereview.domain.post.model.Post
import org.teamsparta.moviereview.domain.post.model.QPost
import org.teamsparta.moviereview.domain.post.model.thumbsup.QThumbsUp
import org.teamsparta.moviereview.domain.users.model.QUsers
import org.teamsparta.moviereview.infra.querydsl.QueryDslSupport

@Repository
class PostRepositoryImpl : CustomPostRepository, QueryDslSupport() {

    private val post = QPost.post
    private val comment = QComment.comment
    private val user = QUsers.users
    private val thumbsUp = QThumbsUp.thumbsUp

    override fun findAllByPageableAndCategory(
        pageable: Pageable,
        category: String?
    ): Triple<Long, List<Post>, List<Long>> {

        val whereClause = BooleanBuilder()

        category?.let { whereClause.and(post.category.eq(Category.fromString(it))) }

        val totalCount = queryFactory.select(post.count()).from(post)
            .where(whereClause)
            .fetchOne() ?: 0L

        val posts = queryFactory.selectFrom(post)
            .leftJoin(post.user, user).fetchJoin()
            .where(whereClause)
            .orderBy(post.createdAt.desc())
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val thumbsUpCounts = queryFactory.select(thumbsUp.count())
            .from(post).leftJoin(thumbsUp)
            .on(thumbsUp.postId.eq(post.id))
            .where(whereClause)
            .groupBy(post.id)
            .orderBy(post.createdAt.desc())
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        return Triple(totalCount, posts, thumbsUpCounts)

    }

    override fun searchPostByPageableAndKeyword(
        pageable: Pageable,
        keyword: String?
    ): Page<PostResponse> {
        val whereClause = BooleanBuilder()

        keyword?.let {
            whereClause.and(
                post.title.containsIgnoreCase(it)
                    .or(post.content.containsIgnoreCase(it))
            )
        }

        val totalCount = queryFactory.select(post.count())
            .from(post)
            .where(whereClause)
            .fetchOne() ?: 0L


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

        val postResponse = posts.map { post ->
            PostResponse(
                id = post.id!!,
                title = post.title,
                nickname = post.user.nickname,
                content = post.content,
                category = post.category.toString(),
                thumbUpCount = thumbsUpCounts[post.id] ?: 0L,
                createdAt = post.createdAt,
                updatedAt = post.updatedAt
            )
        }
        return PageImpl(postResponse, pageable, totalCount)
    }
}