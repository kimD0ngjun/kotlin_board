package com.jun.board.service

import com.jun.board.domain.dto.ReplyDTO
import com.jun.board.domain.dto.toDTO
import com.jun.board.domain.entity.toEntity
import com.jun.board.repository.CommentRepository
import com.jun.board.repository.ReplyRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ReplyService(
    private val replyRepository: ReplyRepository,
    private val commentRepository: CommentRepository
) {
    fun createReply(replyDTO: ReplyDTO): ReplyDTO {
        val comment = commentRepository.findById(replyDTO.commentId).orElseThrow {
            IllegalArgumentException("존재하지 않는 댓글 아이디: ${replyDTO.commentId}") }
        val entity = replyDTO.toEntity(comment)
        val saved = replyRepository.save(entity)
        return saved.toDTO()
    }

    @Transactional(readOnly = true)
    fun getReply(id: Long): ReplyDTO {
        val reply = replyRepository.findById(id).orElseThrow {
            IllegalArgumentException("존재하지 않는 답글 아이디: $id") }
        return reply.toDTO()
    }

    @Transactional(readOnly = true)
    fun getRepliesOfComment(id: Long): List<ReplyDTO> = replyRepository.findByCommentId(id).map { it.toDTO() }

    fun updateReply(replyDTO: ReplyDTO): ReplyDTO? {
        val id = replyDTO.id ?: return null
        val entity =replyRepository.findById(id).orElseThrow {
            IllegalArgumentException("존재하지 않는 답글 아이디: ${replyDTO.id}") }

        if (replyDTO.username != entity.username) throw IllegalArgumentException("작성자가 일치하지 않음")

        entity.apply {
            content = replyDTO.content
        }

        return entity.toDTO()
    }

    fun deleteReply(id: Long) {
        if (replyRepository.existsById(id)) replyRepository.deleteById(id)
    }
}