package com.jun.board.domain.dto

import com.jun.board.domain.entity.Post

data class PostDTO(
    val id: Long? = null,
    val title: String,
    val user: String,
    val content: String?
)

fun Post.toDTO(): PostDTO = PostDTO(
    id = this.id,
    title = this.title,
    user = this.user,
    content = this.content
)

fun PostDTO.toEntity(): Post = Post(
    id = this.id,
    title = this.title,
    user = this.user,
    content = this.content
)