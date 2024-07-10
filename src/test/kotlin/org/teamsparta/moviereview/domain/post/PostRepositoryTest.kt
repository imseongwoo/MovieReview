package org.teamsparta.moviereview.domain.post

import io.kotest.matchers.collections.shouldBeSortedDescendingBy
import io.kotest.matchers.shouldBe
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.ActiveProfiles
import org.teamsparta.moviereview.domain.post.model.Category
import org.teamsparta.moviereview.domain.post.model.Post
import org.teamsparta.moviereview.domain.post.model.thumbsup.ThumbsUp
import org.teamsparta.moviereview.domain.post.repository.v1.PostRepository
import org.teamsparta.moviereview.domain.post.repository.v1.thumbsup.ThumbsUpRepository
import org.teamsparta.moviereview.domain.users.model.Users
import org.teamsparta.moviereview.domain.users.repository.v1.UserRepository

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class PostRepositoryTest @Autowired constructor(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
    private val thumbsUpRepository: ThumbsUpRepository
) {
    @PersistenceContext
    lateinit var entityManager: EntityManager

    @Test
    fun `Category가 입력되지 않았을 때, findAllByPageableAndCategory()를 실행했을때 페이지 사이즈에 맞는 포스트 갯수가 반환되는지 확인`() {
        // GIVEN
        val users = createDefaultUsers()
            .also { userRepository.saveAllAndFlush(it) }

        val posts = createDefaultPosts(users)
            .also { postRepository.saveAllAndFlush(it) }

        createDefaultThumbUps(users, posts)
            .also { thumbsUpRepository.saveAllAndFlush(it) }

        // WHEN
        val (resultCount, resultPosts, resultThumbsUpCounts) =
            postRepository.findAllByPageableAndCategory(PageRequest.of(0, 5), null)

        // THEN
        resultCount shouldBe 10
        resultPosts.size shouldBe 5
        resultThumbsUpCounts.size shouldBe 5
    }

    @Test
    fun `Category를 입력했을을 때, findAllByPageableAndCategory()를 실행했을 때 반환값이 올바른지 확인`() {
        // GIVEN
        val users = createDefaultUsers()
            .also { userRepository.saveAllAndFlush(it) }

        val posts = createDefaultPosts(users)
            .also { postRepository.saveAllAndFlush(it) }

        createDefaultThumbUps(users, posts)
            .also { thumbsUpRepository.saveAllAndFlush(it) }

        // WHEN
        val (resultCount, resultPosts, resultThumbsUpCounts) =
            postRepository.findAllByPageableAndCategory(PageRequest.of(0, 7), "REVIEW")

        // THEN
        resultCount shouldBe 6
        resultPosts.size shouldBe 6
        resultThumbsUpCounts.size shouldBe 6
        resultPosts shouldBeSortedDescendingBy { it.createdAt }

        resultPosts.forEach { it.category shouldBe Category.REVIEW }
    }

    @Test
    fun `검색어를 입력했을 때 searchPostByPageableAndKeyword()를 실행했을 때 반환값이 올바른지 확인`() {
        // GIVEN
        val users = createDefaultUsers()
            .also { userRepository.saveAllAndFlush(it) }

        val posts = createDefaultPosts(users)
            .also { postRepository.saveAllAndFlush(it) }

        createDefaultThumbUps(users, posts)
            .also { thumbsUpRepository.saveAllAndFlush(it) }

        // WHEN
        val result =
            postRepository.searchPostByPageableAndKeyword(PageRequest.of(0, 7), "제목")

        val result2 =
            postRepository.searchPostByPageableAndKeyword(PageRequest.of(0, 7), "내용")

        val result3 =
            postRepository.searchPostByPageableAndKeyword(PageRequest.of(0, 7), "6번")

        // THEN
        result.content.size shouldBe 7
        result.totalPages shouldBe 2
        result.totalElements shouldBe 10

        result2.content.size shouldBe 7
        result2.totalPages shouldBe 2
        result2.totalElements shouldBe 10

        result3.content.size shouldBe 1
        result3.totalPages shouldBe 1
        result3.totalElements shouldBe 1
        result3.content[0].thumbUpCount shouldBe 4L
    }

    private fun createDefaultUsers(): List<Users> {
        return listOf(
            Users(email = "11111@gmail.com", password = """$2a$10z7eLXRe2V423e.2Gwuxik3.btbE7IteB7ui4CIRz4bS7AWzJq1jb6i""", nickname = "1번닉네임"),
            Users(email = "22222@gmail.com", password = """$2a$10z7eLXRe2V423e.2Gwuxik3.btbE7IteB7ui4CIRz4bS7AWzJq1jb6i""", nickname = "2번닉네임"),
            Users(email = "33333@gmail.com", password = """$2a$10z7eLXRe2V423e.2Gwuxik3.btbE7IteB7ui4CIRz4bS7AWzJq1jb6i""", nickname = "3번닉네임"),
            Users(email = "44444@gmail.com", password = """$2a$10z7eLXRe2V423e.2Gwuxik3.btbE7IteB7ui4CIRz4bS7AWzJq1jb6i""", nickname = "4번닉네임"),
            Users(email = "55555@gmail.com", password = """$2a$10z7eLXRe2V423e.2Gwuxik3.btbE7IteB7ui4CIRz4bS7AWzJq1jb6i""", nickname = "5번닉네임")
        )
    }

    private fun createDefaultPosts(users: List<Users>): List<Post> {
        return listOf(
            Post(title = "1번 제목", content = "1번 내용", category = Category.REVIEW, user = users[0]),
            Post(title = "2번 제목", content = "2번 내용", category = Category.INFO, user = users[0]),
            Post(title = "3번 제목", content = "3번 내용", category = Category.REVIEW, user = users[1]),
            Post(title = "4번 제목", content = "4번 내용", category = Category.ETC, user = users[1]),
            Post(title = "5번 제목", content = "5번 내용", category = Category.REVIEW, user = users[2]),
            Post(title = "6번 제목", content = "6번 내용", category = Category.REVIEW, user = users[2]),
            Post(title = "7번 제목", content = "7번 내용", category = Category.INFO, user = users[3]),
            Post(title = "8번 제목", content = "8번 내용", category = Category.REVIEW, user = users[3]),
            Post(title = "9번 제목", content = "9번 내용", category = Category.INFO, user = users[4]),
            Post(title = "10번 제목", content = "10번 내용", category = Category.REVIEW, user = users[4]),
        )
    }

    private fun createDefaultThumbUps(users: List<Users>, posts: List<Post>): List<ThumbsUp> {
        return listOf(
            ThumbsUp(postId = posts[0].id!!, userId = users[0].id!!),
            ThumbsUp(postId = posts[0].id!!, userId = users[1].id!!),
            ThumbsUp(postId = posts[0].id!!, userId = users[2].id!!),
            ThumbsUp(postId = posts[0].id!!, userId = users[3].id!!),
            ThumbsUp(postId = posts[0].id!!, userId = users[4].id!!),
            ThumbsUp(postId = posts[5].id!!, userId = users[0].id!!),
            ThumbsUp(postId = posts[5].id!!, userId = users[1].id!!),
            ThumbsUp(postId = posts[5].id!!, userId = users[2].id!!),
            ThumbsUp(postId = posts[5].id!!, userId = users[3].id!!),
            ThumbsUp(postId = posts[8].id!!, userId = users[0].id!!),
            ThumbsUp(postId = posts[8].id!!, userId = users[1].id!!),
            ThumbsUp(postId = posts[8].id!!, userId = users[2].id!!),
            ThumbsUp(postId = posts[9].id!!, userId = users[0].id!!)
        )
    }
}