package com.jun.board.controller.response

data class PostResponse(
    val id: Long,
    val title: String,
    val username: String,
    val content: String,
)
