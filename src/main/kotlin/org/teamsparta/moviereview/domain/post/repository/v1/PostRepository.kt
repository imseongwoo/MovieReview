package org.teamsparta.moviereview.domain.post.repository.v1

import org.springframework.data.jpa.repository.JpaRepository
import org.teamsparta.moviereview.domain.post.model.Post

interface PostRepository: JpaRepository<Post, Long>, CustomPostRepository