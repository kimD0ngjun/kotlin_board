package com.jun.board.service

import com.jun.board.domain.dto.CommentDTO
import com.jun.board.domain.dto.toEntity
import com.jun.board.domain.entity.toDTO
import com.jun.board.repository.CommentRepository
import com.jun.board.repository.PostRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CommentService(
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository
) {
    fun createComment(commentDTO: CommentDTO): CommentDTO {
        val post = postRepository.findById(commentDTO.postId).orElseThrow{
            IllegalArgumentException("존재하지 않는 게시글 아이디: ${commentDTO.postId}") }
        val entity = commentDTO.toEntity(post)
        val saved = commentRepository.save(entity)
        return saved.toDTO()
    }

    @Transactional(readOnly = true)
    fun getComment(postId: Long, commentId: Long): CommentDTO {
        val post = postRepository.findById(postId)
            .orElseThrow { IllegalArgumentException("존재하지 않는 게시글 아이디: $postId") }

        // 지연로딩 comments 접근 -> SELECT 쿼리 발신
        val comment = post.comments
            .firstOrNull { it.id == commentId }
            ?: throw IllegalArgumentException("존재하지 않는 댓글 아이디: $commentId")

        return comment.toDTO()
    }

    @Transactional(readOnly = true)
    fun getCommentsOfPost(id: Long): List<CommentDTO> = commentRepository.findByPostId(id).map { it.toDTO() }

    fun updateComment(commentDTO: CommentDTO): CommentDTO? {
        val id = commentDTO.id ?: return null
        val entity = commentRepository.findById(id).orElseThrow{
            IllegalArgumentException("존재하지 않는 댓글 아이디: ${commentDTO.id}") }
        /**
         * 자바의 equals()는 코틀린의 ==
         * 자바의 ==는 코틀린의 === (객체 메모리상 참조 비교)
         */
        if (commentDTO.username != entity.username) throw IllegalArgumentException("작성자가 일치하지 않음")

        entity.apply {
            content = commentDTO.content
        }

        return entity.toDTO()
    }

    fun deleteComment(id: Long) {
        if (commentRepository.existsById(id)) commentRepository.deleteById(id)
    }
}