package com.jun.board.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.jun.board.controller.request.CommentRequest
import com.jun.board.domain.dto.CommentDTO
import com.jun.board.domain.dto.PostDTO
import com.jun.board.service.CommentService
import com.jun.board.service.PostService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc(printOnlyOnFailure = false)
class CommentControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    private val objectMapper: ObjectMapper,
    private val postService: PostService,
    private val commentService: CommentService
) {
    val username: String = "작성자"
    val content: String = "댓글"
    var postId: Long? = null // lateinit은 참조 타입에만 가능
    var commentId: Long? = null

    @BeforeEach
    fun setupPost() {
        val postDto = PostDTO(null, "게시글 제목", "게시글 작성자", "게시글 내용")
        val post = postService.createPost(postDto)
        postId = post.id

        val commentDto = CommentDTO(null, postId!!, username, content)
        val comment = commentService.createComment(commentDto)
        commentId = comment.id
    }

    @Test
    @DisplayName("POST /post/postId/comment 테스트")
    fun createComment() {
        val request = CommentRequest(content)

        mockMvc.perform(
            post("/post/$postId/comment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .param("username", username)
        )
            .andExpect(status().isOk)
    }

    @Test
    @DisplayName("GET /post/postId/comment/{id} 테스트")
    fun getComment() {
        mockMvc.perform(
            get("/post/$postId/comment/$commentId")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
    }

    @Test
    @DisplayName("GET /post/postId/comment 테스트")
    fun getAllComments() {
        mockMvc.perform(
            get("/post/$postId/comment")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
    }

    @Test
    @DisplayName("PATCH /post/postId/comment/{id} 테스트")
    fun updateComment() {
        val request = CommentRequest("수정한 댓글 내용")

        mockMvc.perform(
            patch("/post/$postId/comment/$commentId")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .param("username", username)
        )
            .andExpect(status().isOk)
    }

    @Test
    @DisplayName("DELETE /post/postId/comment/{id} 테스트")
    fun deleteComment() {
        mockMvc.perform(
            delete("/post/$postId/comment/$commentId")
                .param("username", username)
        )
            .andExpect(status().isOk)
    }
}