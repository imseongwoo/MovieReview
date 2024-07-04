package org.teamsparta.moviereview.domain.post.repository.v1

import com.querydsl.core.BooleanBuilder
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import org.teamsparta.moviereview.domain.comment.model.QComment
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

    override fun findAllByPageableAndCategory(pageable: Pageable, category: String?): Triple<Long, List<Post>, List<Long>> {

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
}