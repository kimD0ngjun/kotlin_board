package com.jun.board.service

import com.jun.board.domain.dto.PostDTO
import com.jun.board.domain.dto.toEntity
import com.jun.board.domain.entity.Post
import com.jun.board.domain.entity.toDTO
import com.jun.board.repository.PostRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional // 트랜잭션 프록시 때문에 클래스와 메소드에 open 키워드 할당
class PostService(
    private val postRepository: PostRepository
) {
    fun createPost(postDTO: PostDTO): PostDTO {
        val entity = postDTO.toEntity()
        val savedEntity = postRepository.save(entity)
        return savedEntity.toDTO()
    }

    @Transactional(readOnly = true)
    fun getPost(id: Long): PostDTO {
        val post = postRepository.findById(id)
            .orElseThrow { IllegalArgumentException("존재하지 않는 게시글 아이디: $id") }
        return post.toDTO()
    }

    @Transactional(readOnly = true)
    fun getAllPosts(): List<PostDTO> = postRepository.findAll().map { it.toDTO() }

    fun updatePost(postDTO: PostDTO): PostDTO {
        val entity = postDTO.id
            ?. let { postRepository.findById(it).orElse(null) } // let : null 아닐 때만 해당 블록 실행하고 반환
            ?: throw IllegalArgumentException("해당 게시글을 찾을 수 없음")

        entity.apply {
            title = postDTO.title
            username = postDTO.username
            content = postDTO.content
        }

        return entity.toDTO()
    }

    fun deletePost(id: Long, username: String): Unit {
        postRepository.findById(id)
            .orElseThrow { IllegalArgumentException("해당 게시글을 찾을 수 없음") }
            .takeIf { it.username == username } // 조건에 맞으면 객체 그대로 반환 아니면 null 반환 조건부 필터링 함수
            ?.let { postRepository.delete(it) } // 블록 내 null 안전 처리 + 값 반환/변환 후 반환
            ?: throw java.lang.IllegalArgumentException("작성자만 삭제 가능")
    }
}