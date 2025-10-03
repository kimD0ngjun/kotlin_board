package com.jun.board.domain.entity

import com.jun.board.domain.dto.ReplyDTO
import jakarta.persistence.*

@Entity
class Reply(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var user: String,
    @Lob
    var content: String,

    @ManyToOne
    @JoinColumn(name = "content_id")
    var comment: Comment? = null
)

fun ReplyDTO.toEntity(comment: Comment): Reply = Reply(
    id = this.id,
    user = this.user,
    content = this.content,
    comment = comment
)