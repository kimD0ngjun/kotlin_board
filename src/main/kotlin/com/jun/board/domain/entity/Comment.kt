package com.jun.board.domain.entity

import jakarta.persistence.*

@Entity
class Comment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var user: String,
    @Lob
    var content: String,

    @ManyToOne
    @JoinColumn(name = "post_id")
    var post: Post? = null,

    @OneToMany(mappedBy = "comment", cascade = [CascadeType.ALL], orphanRemoval = true)
    var replies: MutableList<Reply> = mutableListOf()
)