package com.jun.board.controller

import com.jun.board.controller.request.CommentRequest
import com.jun.board.controller.request.toDto
import com.jun.board.controller.response.CommentResponse
import com.jun.board.domain.dto.toResponse
import com.jun.board.service.CommentService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/posts/{postId}/comments")
class CommentController(
    private val commentService: CommentService
) {
    @PostMapping
    fun createComment(
        @PathVariable postId: Long,
        @RequestBody commentRequest: CommentRequest,
        username: String
    ): CommentResponse = commentService
        .createComment(commentRequest.toDto(username, postId))
        .toResponse()

    @GetMapping("/{commentId}")
    fun getComment(
        @PathVariable postId: Long,
        @PathVariable commentId: Long
    ): CommentResponse = commentService.getComment(postId, commentId).toResponse()
}