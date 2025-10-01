package com.jun.board.repository

import com.jun.board.domain.entity.Reply
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReplyRepository: JpaRepository<Reply, Long> {
}