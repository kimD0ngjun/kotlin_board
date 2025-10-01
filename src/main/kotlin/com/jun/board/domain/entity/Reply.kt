package com.jun.board.domain.entity

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