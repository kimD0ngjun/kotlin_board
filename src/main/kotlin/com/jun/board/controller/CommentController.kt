package com.jun.board.controller

import com.jun.board.controller.request.CommentRequest
import com.jun.board.controller.request.toDto
import com.jun.board.controller.response.CommentResponse
import com.jun.board.domain.dto.toResponse
import com.jun.board.service.CommentService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/post/{postId}/comment")
class CommentController(
    private val commentService: CommentService
) {
    @PostMapping
    fun createComment(
        @PathVariable("postId") postId: Long,
        @RequestBody commentRequest: CommentRequest,
        username: String
    ): CommentResponse = commentService
        .createComment(commentRequest.toDto(username, postId))
        .toResponse()

    @GetMapping("/{commentId}")
    fun getComment(
        @PathVariable("postId") postId: Long,
        @PathVariable("commentId") commentId: Long
    ): CommentResponse = commentService.getComment(postId, commentId).toResponse()

    @GetMapping
    fun getAllComments(@PathVariable postId: Long): List<CommentResponse> =
        commentService.getCommentsOfPost(postId).map { it.toResponse() }

    @PatchMapping // 얘도 그냥 pathVariable 주는 게 맞지 않나?
    fun updateComment(
        @PathVariable("postId") postId: Long,
        @RequestBody commentRequest: CommentRequest,
        username: String
    ): CommentResponse = commentService.updateComment(commentRequest.toDto(username, postId)).toResponse()

    @DeleteMapping("/{commentId}")
    fun deleteComment(
        @PathVariable("postId") postId: Long,
        @PathVariable("commentId") commentId: Long,
        username: String
    ): Unit = commentService.deleteComment(commentId, postId, username)
}