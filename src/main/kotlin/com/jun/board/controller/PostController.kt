package com.jun.board.controller

import com.jun.board.controller.request.PostRequest
import com.jun.board.controller.request.toDto
import com.jun.board.controller.response.PostResponse
import com.jun.board.domain.dto.toResponse
import com.jun.board.service.PostService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/post")
class PostController(
    private val postService: PostService
) {
    @PostMapping
    fun createPost(
        @RequestBody postRequest: PostRequest,
        username: String // 나중에 @AuthenticationPrincipal userDetails: UserDetailsImpl 로 교체
    ): PostResponse = postService.createPost(postRequest.toDto(username)).toResponse()

    @GetMapping("/{id}")
    fun getPost(
        @PathVariable("id") id: Long
    ): PostResponse = postService.getPost(id).toResponse()

    @GetMapping
    fun getAllPosts(): List<PostResponse> = postService.getAllPosts().map { it.toResponse() }

    @PatchMapping
    fun updatePost(
        @RequestBody postRequest: PostRequest,
        username: String // 나중에 @AuthenticationPrincipal userDetails: UserDetailsImpl 로 교체
    ): PostResponse = postService.updatePost(postRequest.toDto(username)).toResponse()

    @DeleteMapping("/{id}")
    fun deletePost(
        @PathVariable("id") id: Long,
        username: String // 나중에 @AuthenticationPrincipal userDetails: UserDetailsImpl 로 교체
    ): Unit = postService.deletePost(id, username)
}