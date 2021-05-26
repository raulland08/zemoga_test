package com.zemoga.framework.di

import com.zemoga.data.CommentsSource
import com.zemoga.data.PostsRepository
import com.zemoga.data.PostsSource
import com.zemoga.data.UsersSource
import com.zemoga.data.api.comment.CommentServiceApi
import com.zemoga.data.api.post.PostServiceApi
import com.zemoga.data.api.user.UserServiceApi
import com.zemoga.data.network.NetworkController
import com.zemoga.data.network.NetworkRepository
import com.zemoga.data.network.NetworkSource
import com.zemoga.domain.usecase.*
import com.zemoga.ui.PostsVM
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val usersModule = module(override = true) {
    viewModel { PostsVM(get(), get(), get(), get(), get(), get()) }

    single { GetPostsUseCase(get()) }
    single { GetUsersUseCase(get()) }
    single { GetCommentsUseCase(get()) }
    single { UpdatePostUseCase(get()) }
    single { DeleteAllPostsUseCase(get()) }

    single { PostsRepository(get(), get(), get(), get(), get(), get()) }

    single { NetworkRepository(get()) }
    single { NetworkSource(get()) }
    single { NetworkController(androidContext()) }

    single { PostsSource() }
    single { UsersSource() }
    single { CommentsSource() }

    single { get<Retrofit>().create(PostServiceApi::class.java) }
    single { get<Retrofit>().create(UserServiceApi::class.java) }
    single { get<Retrofit>().create(CommentServiceApi::class.java) }

}

