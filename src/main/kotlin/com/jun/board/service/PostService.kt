package com.jun.board.service

import com.jun.board.repository.PostRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional // 트랜잭션 프록시 때문에 클래스와 메소드에 open 키워드 할당
class PostService(
    private val postRepository: PostRepository
) {
}