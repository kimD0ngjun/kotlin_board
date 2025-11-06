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

    fun updateComment(commentDTO: CommentDTO): CommentDTO {
        val entity = commentDTO.id
            ?. let { commentRepository.findById(it).orElse(null) }
            ?: throw IllegalArgumentException("존재하지 않는 댓글 아이디: ${commentDTO.id}")

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

    fun deleteComment(id: Long, postId: Long, username: String): Unit {
        val post = postRepository.findById(postId)
            .orElseThrow { IllegalArgumentException("해당 게시글을 찾을 수 없음") }

        commentRepository.findById(id)
            .orElseThrow { IllegalArgumentException("해당 댓글을 찾을 수 없음") }
            .run { // 객체를 람다로 변환해서 나온 람다의 결괏값 반환
                when {
                    post.id != this.post?.id ->
                        throw IllegalStateException("댓글이 해당 게시글에 속하지 않음") // 조건 1
                    this.username != username ->
                        throw IllegalAccessException("작성자만 삭제 가능") // 조건 2
                    else ->
                        commentRepository.delete(this) // 조건 3
                }
            }
    }
}