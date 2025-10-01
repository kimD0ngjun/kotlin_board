package com.jun.board.domain.dto

import com.jun.board.domain.entity.Comment
import com.jun.board.domain.entity.Post

data class CommentDTO(
    val id: Long? = null,
    val postId: Long,
    val user: String,
    val content: String
)

fun Comment.toDTO(): CommentDTO = CommentDTO(
    id = this.id,
    postId = this.post?.id ?: 0L,
    user = this.user,
    content = this.content
)

fun CommentDTO.toEntity(post: Post): Comment = Comment(
    id = this.id,
    user = this.user,
    content = this.content,
    post = post
)