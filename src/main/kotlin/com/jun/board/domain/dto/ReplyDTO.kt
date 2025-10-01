package com.jun.board.domain.dto

import com.jun.board.domain.entity.Comment
import com.jun.board.domain.entity.Reply

data class ReplyDTO(
    val id: Long?,
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

fun ReplyDTO.toEntity(comment: Comment): Reply = Reply(
    id = this.id,
    user = this.user,
    content = this.content,
    comment = comment
)