package com.jun.board.service

import com.jun.board.domain.dto.CommentDTO
import com.jun.board.domain.dto.PostDTO
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class CommentServiceTest @Autowired constructor(
    private val commentService: CommentService,
    private val postService: PostService
) {
    lateinit var post: PostDTO
    lateinit var comment1: CommentDTO
    lateinit var comment2: CommentDTO

    @BeforeEach
    fun setUp() {
        val postDTO = PostDTO(null, "글 제목", "글 작성자", "글 내용")
        post = postService.createPost(postDTO)

        val dto1 = CommentDTO(null, post.id!!, "댓글 작성자1", "댓11")
        val dto2 = CommentDTO(null, post.id!!, "댓글 작성자2", "댓22")
        comment1 = commentService.createComment(dto1)
        comment2 = commentService.createComment(dto2)
    }

    @Test
    fun createComment() {
    }

    @Test
    fun getComment() {
    }

    @Test
    fun getCommentsOfPost() {
    }

    @Test
    fun updateComment() {
    }

    @Test
    fun deleteComment() {
    }

}