package com.jun.board.domain.dto

import com.jun.board.domain.entity.Comment
import com.jun.board.domain.entity.Reply

data class ReplyDTO(
    val id: Long? = null,
    val commentId: Long,
    val user: String,
    val content: String
)

fun Reply.toDTO(): ReplyDTO = ReplyDTO(
    id = this.id,
    commentId = this.comment?.id ?: 0L,
    user = this.user,
    content = this.content
)