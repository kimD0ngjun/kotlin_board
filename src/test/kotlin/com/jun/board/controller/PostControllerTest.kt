package com.jun.board.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.jun.board.controller.request.PostRequest
import com.jun.board.service.PostService
import io.kotest.core.spec.style.FunSpec
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

//@WebMvcTest(PostController::class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class PostControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    private val objectMapper: ObjectMapper
) {
    val postService: PostService = mockk<PostService>()

    @BeforeEach
    fun setUp() {
        every { postService.createPost(any()) } returns mockk(relaxed = true)
    }

    @Test
    fun test() {
        val request = PostRequest("제목", "내용")

        val result = mockMvc.perform(
            post("/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .param("username", "test")
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk)
            .andReturn()
    }
}
//@ExtendWith(SpringExtension::class)
//internal class PostControllerTest: FunSpec({
//    lateinit var mockMvc: MockMvc
//    var objectMapper = ObjectMapper()
//    val postService: PostService = mockk<PostService>()
//
//    beforeSpec {
//        // mock bean 스프링 컨텍스트에서 교체 수동 주입
//        // mockk(relaxed = true) : 아무 설정 없어도 호출 시 예외 x, 기본값 자동 반환
//        every { postService.createPost(any()) } returns mockk(relaxed = true) // 아무 설정 없어도 호출 시 예외 x, 기본값 자동 반환
//    }
//
//    test("POST /post 요청 시 200 응답 반환") {
//        val request = PostRequest("제목", "내용")
//
//        val result = mockMvc.perform(
//            post("/post")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(request))
//        )
//            .andDo(MockMvcResultHandlers.print())
//            .andExpect(status().isOk)
//            .andReturn()
//    }
//})