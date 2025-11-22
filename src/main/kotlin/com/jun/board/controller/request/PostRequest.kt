package com.jun.board.controller.request

import com.jun.board.domain.dto.PostDTO

data class PostRequest(
    val title: String,
    val content: String?
)

fun PostRequest.toDto(username: String): PostDTO = PostDTO(
    title = this.title,
    username = username,
    content = this.content
)

fun PostRequest.toDto(username: String, postId: Long): PostDTO = PostDTO(
    id = postId,
    title = this.title,
    username = username,
    content = this.content
)