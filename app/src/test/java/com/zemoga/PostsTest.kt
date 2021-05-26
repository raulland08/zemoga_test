package com.zemoga

import com.zemoga.data.PostsRepository
import com.zemoga.domain.entity.CommentEntity
import com.zemoga.domain.entity.PostEntity
import com.zemoga.domain.entity.UserEntity
import com.zemoga.domain.usecase.*
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class PostsTest : BaseZemogaTest() {

    private lateinit var postsRepository: PostsRepository

    private lateinit var postEntityList: List<PostEntity>
    private lateinit var postLocalEntityList: MutableList<PostEntity>
    private lateinit var userEntityList: List<UserEntity>
    private lateinit var commentEntityList: List<CommentEntity>

    private lateinit var getPostsUseCase: GetPostsUseCase
    private lateinit var getUsersUseCase: GetUsersUseCase
    private lateinit var getCommentsUseCase: GetCommentsUseCase
    private lateinit var updatePostUseCase: UpdatePostUseCase
    private lateinit var deleteAllPostsUseCase: DeleteAllPostsUseCase

    @Before
    fun setUp() {

        postsRepository = mockk()

        getPostsUseCase = GetPostsUseCase(postsRepository)
        getUsersUseCase = GetUsersUseCase(postsRepository)
        getCommentsUseCase = GetCommentsUseCase(postsRepository)
        updatePostUseCase = UpdatePostUseCase(postsRepository)
        deleteAllPostsUseCase = DeleteAllPostsUseCase(postsRepository)

        postEntityList = listOf(
            PostEntity(id = 1, userId = 1, title = "title1", body = "body1", isRead = false, isFavorite = false),
            PostEntity(id = 2, userId = 1, title = "title2", body = "body2", isRead = true, isFavorite = true),
            PostEntity(id = 3, userId = 2, title = "title3", body = "body3", isRead = false, isFavorite = false)
        )

        postLocalEntityList = mutableListOf(
            PostEntity(id = 1, userId = 1, title = "title1_local", body = "body1", isRead = false, isFavorite = false)
        )

        userEntityList = listOf(
            UserEntity(id = 1, name = "name1", email = "email1", phone = "phone1", website = "website1"),
            UserEntity(id = 2, name = "name2", email = "email2", phone = "phone2", website = "website2")
        )

        commentEntityList = listOf(
            CommentEntity(id = 1, postId = 1, name = "name1", body = "body1"),
            CommentEntity(id = 2, postId = 2, name = "name2", body = "body2")
        )

        //Given
        every { postsRepository.updatePostStatus(any()) } answers {}
        every { postsRepository.deleteAll() } answers {}
        coEvery { postsRepository.getPosts() } returns postEntityList
        coEvery { postsRepository.getUsers() } returns userEntityList
        coEvery { postsRepository.getComments() } returns commentEntityList
        every { postsRepository.getLocalPosts() } returns postLocalEntityList
    }

    @Test
    fun `GetPostsUseCase must call the posts only from endpoint if there are no local posts cached`() = runBlocking {

        //Given
        every { postsRepository.getLocalPosts() } returns mutableListOf()

        // When
        getPostsUseCase()

        // Then
        coVerify {
            postsRepository.getPosts()
        }
    }

    @Test
    fun `GetPostsUseCase must call the posts only from realm if there are local posts cached`() = runBlocking {

        // When
        getPostsUseCase()

        // Then
        verify {
            postsRepository.getLocalPosts()
        }

        coVerify(exactly = 0) {
            postsRepository.getPosts()
        }
    }

    @Test
    fun `GetUsersUseCase must call the users endpoint`() = runBlocking {

        // When
        getUsersUseCase()

        // Then
        coVerify {
            postsRepository.getUsers()
        }
    }

    @Test
    fun `GetCommentsUseCase must call the users endpoint`() = runBlocking {

        // When
        getCommentsUseCase()

        // Then
        coVerify {
            postsRepository.getComments()
        }
    }

    @Test
    fun `UpdatePostUseCase must call the update post endpoint`() = runBlocking {

        // When
        updatePostUseCase(postEntityList[0])

        // Then
        coVerify {
            postsRepository.updatePostStatus(postEntityList[0])
        }
    }

    @Test
    fun `DeleteAllPostsUseCase must call the delete all posts from realm method`() = runBlocking {

        // When
        deleteAllPostsUseCase()

        // Then
        verify {
            postsRepository.deleteAll()
        }
    }
}