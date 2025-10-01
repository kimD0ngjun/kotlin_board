package com.jun.board.domain.entity

import jakarta.persistence.*

@Entity
class Comment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    var user: String,
    @Lob
    var content: String
)