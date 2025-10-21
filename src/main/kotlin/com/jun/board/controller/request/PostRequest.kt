package com.jun.board.controller.request

import com.jun.board.domain.dto.PostDTO

data class PostRequest(
    val title: String,
    val content: String?
)

fun PostRequest.toDto(username: String): PostDTO = PostDTO(
    id = null,
    title = this.title,
    username = username,
    content = this.content
)