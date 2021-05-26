package com.zemoga.ui

import androidx.lifecycle.*
import com.zemoga.domain.entity.CommentEntity
import com.zemoga.domain.entity.PostEntity
import com.zemoga.domain.entity.UserEntity
import com.zemoga.domain.usecase.*
import com.zemoga.domain.usecase.network.HaveConnectivityUseCase
import kotlinx.coroutines.launch

class PostsVM(
    private val getPosts: GetPostsUseCase,
    private val getUsers: GetUsersUseCase,
    private val getComments: GetCommentsUseCase,
    private val updatePost: UpdatePostUseCase,
    private val deleteAllPosts: DeleteAllPostsUseCase,
    private val hasConnectivityUseCase: HaveConnectivityUseCase
): ViewModel() {

    private val _posts: MutableLiveData<List<PostEntity>> = MutableLiveData()
    val posts: LiveData<List<PostEntity>> get() = _posts

    private val _post: MutableLiveData<PostEntity> = MutableLiveData()
    val post: LiveData<PostEntity> get() = _post

    private val _users: MutableLiveData<List<UserEntity>> = MutableLiveData()
    private val users: LiveData<List<UserEntity>> get() = _users

    private val _user: MutableLiveData<UserEntity> = MutableLiveData()
    val user: LiveData<UserEntity> get() = _user

    private val _allComments: MutableLiveData<List<CommentEntity>> = MutableLiveData()
    private val allComments: LiveData<List<CommentEntity>> get() = _allComments

    private val _relatedComments: MutableLiveData<List<CommentEntity>> = MutableLiveData()
    val relatedComments: LiveData<List<CommentEntity>> get() = _relatedComments

    private val _arePostsDeleted = MutableLiveData(false)
    val arePostsDeleted: LiveData<Boolean> get() = _arePostsDeleted

    private val _isErrorThrown = MutableLiveData(false)
    val isErrorThrown: LiveData<Boolean> get() = _isErrorThrown

    fun initLoading() {
        getPosts()
        getUsers()
        getComments()
    }

    private fun getPosts() {
        viewModelScope.launch {
            try {
                _posts.postValue(getPosts.invoke())
            } catch (e: Exception) {
                _isErrorThrown.postValue(true)
            }
        }
    }

    private fun getUsers() {
        viewModelScope.launch {
            try {
                _users.postValue(getUsers.invoke())
            } catch (e: Exception) {
                _isErrorThrown.postValue(true)
            }
        }
    }

    private fun getComments() {
        viewModelScope.launch {
            try {
                _allComments.postValue(getComments.invoke())
            } catch (e: Exception) {
                _isErrorThrown.postValue(true)
            }
        }
    }

    fun hasConnectivity(): LiveData<Boolean> {
        return liveData {
            emit(hasConnectivityUseCase())
        }
    }

    fun itemClicked(id: Int) {
        _post.value = posts.value!!.find { postId -> id == postId.id }
        _post.value?.isRead = true

        post.value?.let {
            userInfoByPostId(it)
            commentsByPostId(it)
            updatePost(it)
        }
    }

    private fun userInfoByPostId(postSelected: PostEntity) {
        _user.value = users.value!!.find { userId -> postSelected.userId == userId.id}
    }

    private fun commentsByPostId(postSelected: PostEntity) {
        _relatedComments.value = allComments.value!!.filter { commentId -> postSelected.id == commentId.postId}
    }

    private fun updatePost(post: PostEntity) {
        updatePost.invoke(post)
    }

    fun favoriteClicked() {
        _post.value?.isFavorite = !(_post.value!!.isFavorite)
        updatePost(post.value!!)
    }

    fun favoriteTabClicked() {
      _posts.postValue(posts.value!!.filter { post -> post.isFavorite })
    }

    fun allPostsTabClicked() {
        if (arePostsDeleted.value != null && !arePostsDeleted.value!!) getPosts()
    }

    fun deleteFabClicked() {
        deleteAllPosts.invoke()
        _posts.value = mutableListOf()
        _arePostsDeleted.postValue(true)
    }

    fun refreshIconClicked() {
        initLoading()
        _arePostsDeleted.postValue(false)
    }
}