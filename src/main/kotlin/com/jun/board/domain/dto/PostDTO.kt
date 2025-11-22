package com.jun.board.domain.dto

import com.jun.board.controller.response.PostResponse
import com.jun.board.domain.entity.Post

data class PostDTO(
    val id: Long? = null,
    val title: String,
    val username: String,
    val content: String?
)

fun PostDTO.toEntity(): Post = Post(
    title = this.title,
    username = this.username,
    content = this.content
)

fun PostDTO.toResponse(): PostResponse = PostResponse(
    id = this.id ?: 0L,
    title = this.title,
    username = this.username,
    content = this.content ?: ""
)