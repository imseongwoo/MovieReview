package org.teamsparta.moviereview.domain.post

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.ActiveProfiles
import org.teamsparta.moviereview.domain.post.repository.v1.PostRepository
import org.teamsparta.moviereview.domain.post.service.v1.PostService
import org.teamsparta.moviereview.domain.post.service.v2.PostService2
import kotlin.test.Test

@SpringBootTest
@ActiveProfiles("test")
class PostServiceTest {

    @MockkBean
    private lateinit var postRepository: PostRepository

    @Autowired
    private lateinit var postService: PostService

    @Autowired
    private lateinit var postService2: PostService2

    @Test
    fun `캐시를 적용하지 않았을때 같은 변수로 searchPost()를 호출하면 매번 DB에서 값을 가져온다`() {

        val pageable = PageRequest.of(0, 10)
        val keyword = "keyword"

        every { postRepository.searchPostByPageableAndKeyword(pageable, keyword) } returns Page.empty()

        postService.searchPost(pageable, keyword)

        verify(exactly = 1) { postRepository.searchPostByPageableAndKeyword(pageable, keyword) }

        repeat(5) {postService.searchPost(pageable, keyword) }

        verify(exactly = 6) { postRepository.searchPostByPageableAndKeyword(pageable, keyword) }

    }

    @Test
    fun `In-Memory 캐시를 적용한 v2에서 같은 변수로 searchPost()를 호출해도 DB에서 값은 딱 한 번만 가져온다`() {

        val pageable = PageRequest.of(0, 10)
        val keyword = "keyword"

        every { postRepository.searchPostByPageableAndKeyword(pageable, keyword) } returns Page.empty()

        postService2.searchPostByKeyword(pageable, keyword)

        verify(exactly = 1) { postRepository.searchPostByPageableAndKeyword(pageable, keyword) }

        repeat(5) {postService2.searchPostByKeyword(pageable, keyword) }

        verify(exactly = 1) { postRepository.searchPostByPageableAndKeyword(pageable, keyword) }

    }

}