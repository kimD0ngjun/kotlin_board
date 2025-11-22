package com.jun.board.domain.dto

import com.jun.board.controller.response.CommentResponse
import com.jun.board.domain.entity.Comment
import com.jun.board.domain.entity.Post

data class CommentDTO(
    val id: Long? = null,
    val postId: Long,
    val username: String,
    val content: String
)

fun CommentDTO.toEntity(post: Post): Comment = Comment(
    username = this.username,
    content = this.content,
    post = post
)

fun CommentDTO.toResponse(): CommentResponse = CommentResponse(
    id = this.id ?: 0L,
    postId = this.postId,
    username = this.username,
    content = this.content
)