package com.jun.board.service

import com.jun.board.domain.dto.CommentDTO
import com.jun.board.domain.dto.PostDTO
import com.jun.board.domain.dto.ReplyDTO
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import kotlin.test.assertEquals

@SpringBootTest
@ActiveProfiles("test")
class ReplyServiceTest @Autowired constructor(
    private val replyService: ReplyService,
    private val commentService: CommentService,
    private val postService: PostService
) {
    lateinit var post: PostDTO
    lateinit var comment: CommentDTO
    lateinit var reply1: ReplyDTO
    lateinit var reply2: ReplyDTO

    @BeforeEach
    fun setUp() {
        val postDTO = PostDTO(null, "글 제목", "글 작성자", "글 내용")
        post = postService.createPost(postDTO)
        val commentDTO = CommentDTO(null, post.id!!, "댓글 작성자", "댓글")
        comment = commentService.createComment(commentDTO)

        val dto1 = ReplyDTO(null, comment.id!!, "답글 작성자1", "답글11")
        val dto2 = ReplyDTO(null, comment.id!!, "답글 작성자2", "답글222")
        reply1 = replyService.createReply(dto1)
        reply2 = replyService.createReply(dto2)
    }

    @Test
    @DisplayName("답글 작성 테스트")
    fun createReply() {
        val dto = ReplyDTO(null, comment.id!!, "새로운 답글 작성자", "새로운 답글33")
        val reply = replyService.createReply(dto)

        assertEquals(comment.id!!, reply.commentId)
        assertEquals("새로운 답글 작성자", reply.username)
        assertEquals("새로운 답글33", reply.content)
    }

    @Test
    @DisplayName("답글 조회 테스트")
    fun getReply() {
        val fetched1 = replyService.getReply(reply1.id!!)
        val fetched2 = replyService.getReply(reply2.id!!)
        val exception = assertThrows<IllegalArgumentException> { replyService.getReply(999L) }

        Assertions.assertNotNull(fetched1)
        Assertions.assertEquals(reply1.username, fetched1?.username)
        Assertions.assertEquals(reply1.content, fetched1?.content)

        Assertions.assertNotNull(fetched2)
        Assertions.assertEquals(reply2.username, fetched2?.username)
        Assertions.assertEquals(reply2.content, fetched2?.content)

        assertEquals("존재하지 않는 답글 아이디: 999", exception.message)
    }

    @Test
    @DisplayName("특정 댓글의 답글 전체 조회 테스트")
    fun getRepliesOfComment() {
        val replyList: List<ReplyDTO> = replyService.getRepliesOfComment(comment.id!!)
        assertTrue(replyList.any { it.id == reply1.id })
        assertTrue(replyList.any { it.id == reply2.id })
    }

    @Test
    @DisplayName("답글 수정 테스트")
    fun updateReply() {
        var updateDto = ReplyDTO(reply1.id!!, reply1.commentId, "잘못된 답글 작성자1", "수정답글11")
        val exception = assertThrows<IllegalArgumentException> {
            replyService.updateReply(updateDto) }
        assertEquals("작성자가 일치하지 않음", exception.message)

        updateDto = ReplyDTO(reply1.id!!, reply1.commentId, "답글 작성자1", "수정한 답글11")
        val updateReply = replyService.updateReply(updateDto)

        assertEquals(updateDto.username, updateReply?.username)
        assertEquals(updateDto.content, updateReply?.content)
    }

    @Test
    @DisplayName("답글 삭제 테스트")
    fun deleteReply() {
        replyService.deleteReply(reply1.id!!)
        assertThrows<IllegalArgumentException> { replyService.getReply(reply1.id!!) }

        // 연관관계 테스트
        postService.deletePost(post.id!!)
        assertThrows<IllegalArgumentException> { replyService.getReply(reply2.id!!) }
    }

}