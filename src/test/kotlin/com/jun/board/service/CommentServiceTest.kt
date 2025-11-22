package com.jun.board.service

import com.jun.board.domain.dto.CommentDTO
import com.jun.board.domain.dto.PostDTO
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
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
    @DisplayName("댓글 작성 테스트")
    fun createComment() {
        val dto = CommentDTO(null, post.id!!, "새로운 댓글 작성자", "새로 작성한 댓글입니다33")
        val comment = commentService.createComment(dto)

        assertEquals(post.id!!, comment.postId)
        assertEquals("새로운 댓글 작성자", comment.username)
        assertEquals("새로 작성한 댓글입니다33", comment.content)
    }

    @Test
    @DisplayName("댓글 조회 테스트")
    fun getComment() {
        val fetched1 = commentService.getComment(comment1.postId, comment1.id!!)
        val fetched2 = commentService.getComment(comment1.postId, comment2.id!!)

        assertNotNull(fetched1)
        assertEquals(comment1.username, fetched1.username)
        assertEquals(comment1.content, fetched1.content)

        assertNotNull(fetched2)
        assertEquals(comment2.username, fetched2.username)
        assertEquals(comment2.content, fetched2.content)

        val exception = assertThrows<IllegalArgumentException> {
            commentService.getComment(comment1.postId, 999L)
        }
        assertEquals("존재하지 않는 댓글 아이디: 999", exception.message)
    }

    @Test
    @DisplayName("특정 게시물의 댓글 전체 조회 테스트")
    fun getCommentsOfPost() {
        val commentList: List<CommentDTO> = commentService.getCommentsOfPost(post.id!!)
        // 실제 조회된 애들 중 내가 사전초기화한 DTO가 존재하는지
        assertTrue(commentList.any { it.id == comment1.id })
        assertTrue(commentList.any { it.id == comment2.id })
    }

    @Test
    @DisplayName("댓글 수정 테스트")
    fun updateComment() {
        var updateDto = CommentDTO(comment1.id!!, comment1.postId, "잘못된 댓글 작성자1", "수정댓11")
        val exception = assertThrows<IllegalArgumentException> {
            commentService.updateComment(updateDto)
        }
        assertEquals("작성자가 일치하지 않음", exception.message)

        updateDto = CommentDTO(comment1.id!!, comment1.postId, "댓글 작성자1", "수정댓11")
        val updateComment = commentService.updateComment(updateDto)

        assertEquals(updateDto.username, updateComment.username)
        assertEquals(updateDto.content, updateComment.content)
    }

    @Test
    @DisplayName("댓글 삭제 테스트")
    fun deleteComment() {
        commentService.deleteComment(comment1.id!!, comment1.postId, comment1.username)
        assertThrows<IllegalArgumentException> { commentService.getComment(comment1.postId, comment1.id!!) }
    }

}