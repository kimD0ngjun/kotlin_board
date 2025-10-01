package com.jun.board.domain.entity

import jakarta.persistence.*

@Entity // JPA Hibernate는 런타임에 프록시를 생성해야 돼서 상속이 요구되므로 open이 강제된다
class Post(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var title: String,
    var user: String,
    @Lob
    var content: String? = null, // 선택적 파라미터가 되면서 클래스 필드로 자연스레 편입됨

    @OneToMany(mappedBy = "post", cascade = [CascadeType.ALL], orphanRemoval = true)
    var comments: MutableList<Comment> = mutableListOf()
)