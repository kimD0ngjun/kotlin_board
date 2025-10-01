package com.jun.board.service

import com.jun.board.repository.ReplyRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ReplyService(
    private val replyRepository: ReplyRepository
) {
}