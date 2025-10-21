package com.jun.board.controller

import com.jun.board.controller.request.PostRequest
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
    ) {}

    @GetMapping("/{id}")
    fun getPost(
        @PathVariable("id") id: Long
    ) {}

    @GetMapping
    fun getAllPosts() {}

    @PatchMapping
    fun updatePost(
        @RequestBody postRequest: PostRequest,
        username: String // 나중에 @AuthenticationPrincipal userDetails: UserDetailsImpl 로 교체
    ) {}

    @DeleteMapping("/{id}")
    fun deletePost(
        @PathVariable("id") id: Long,
        username: String // 나중에 @AuthenticationPrincipal userDetails: UserDetailsImpl 로 교체
    ) {}
}