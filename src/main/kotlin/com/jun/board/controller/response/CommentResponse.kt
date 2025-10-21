package com.jun.board.controller.response

data class CommentResponse(
    val postId: Long,
    val username: String,
    val content: String
)
