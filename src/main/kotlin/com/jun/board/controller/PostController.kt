package com.jun.board.controller

import com.jun.board.service.PostService
import org.springframework.web.bind.annotation.*

@RestController
class PostController(
    private val postService: PostService
) {
    @PostMapping
    fun createPost() {}

    @GetMapping
    fun getPost() {}

    @GetMapping
    fun getAllPosts() {}

    @PatchMapping
    fun updatePost() {}

    @DeleteMapping
    fun deletePost() {}
}