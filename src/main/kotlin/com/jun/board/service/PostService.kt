package com.jun.board.service

import com.jun.board.domain.dto.PostDTO
import com.jun.board.domain.dto.toEntity
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

    /**
     * Retrieves the post with the given id and returns it as a PostDTO.
     *
     * @param id The id of the post to retrieve.
     * @return The matching PostDTO.
     * @throws IllegalArgumentException if no post exists with the given id (message: "존재하지 않는 게시글 아이디: $id").
     */
    @Transactional(readOnly = true)
    fun getPost(id: Long): PostDTO? {
        val post = postRepository.findById(id)
            .orElseThrow { IllegalArgumentException("존재하지 않는 게시글 아이디: $id") }
        return post.toDTO()
    }

    @Transactional(readOnly = true)
    fun getAllPosts(): List<PostDTO> = postRepository.findAll().map { it.toDTO() }

    fun updatePost(postDTO: PostDTO): PostDTO? {
        val id = postDTO.id ?: return null
        val entity = postRepository.findById(id).orElse(null) ?: return null

        entity.apply {
            title = postDTO.title
            username = postDTO.username
            content = postDTO.content
        }

        return entity.toDTO()
    }

    fun deletePost(id: Long): Unit {
        if (postRepository.existsById(id)) postRepository.deleteById(id)
    }
}