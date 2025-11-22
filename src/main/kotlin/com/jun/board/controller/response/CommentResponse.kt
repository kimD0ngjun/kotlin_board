package com.jun.board.controller.response

data class CommentResponse(
    val id: Long,
    val postId: Long,
    val username: String,
    val content: String
)
