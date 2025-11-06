package com.jun.board.controller.request

import com.jun.board.domain.dto.CommentDTO

data class CommentRequest(
    val content: String
)

fun CommentRequest.toDto(username: String, postId: Long): CommentDTO = CommentDTO(
    username = username,
    content = this.content,
    postId = postId
)

fun CommentRequest.toDto(username: String, postId: Long, id: Long): CommentDTO = CommentDTO(
    id = id,
    username = username,
    content = this.content,
    postId = postId
)