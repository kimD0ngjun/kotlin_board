package com.jun.board.domain.entity

import com.jun.board.domain.dto.CommentDTO
import jakarta.persistence.*

@Entity
class Comment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var username: String,
    @Lob
    var content: String,

    @ManyToOne
    @JoinColumn(name = "post_id")
    var post: Post? = null,

    @OneToMany(mappedBy = "comment", cascade = [CascadeType.ALL], orphanRemoval = true)
    var replies: MutableList<Reply> = mutableListOf()
)

fun Comment.toDTO(): CommentDTO = CommentDTO(
    id = this.id,
    postId = this.post?.id ?: 0L,
    username = this.username,
    content = this.content
)