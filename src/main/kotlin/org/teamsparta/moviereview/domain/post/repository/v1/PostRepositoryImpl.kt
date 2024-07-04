package org.teamsparta.moviereview.domain.post.repository.v1

import org.springframework.stereotype.Repository
import org.teamsparta.moviereview.infra.querydsl.QueryDslSupport

@Repository
class PostRepositoryImpl : CustomPostRepository, QueryDslSupport()