package com.jun.board.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.jun.board.controller.request.PostRequest
import com.jun.board.service.PostService
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

//@WebMvcTest(PostController::class)
@SpringBootTest
@AutoConfigureMockMvc
internal class PostControllerTest: DescribeSpec() {
    val postService: PostService = mockk<PostService>()
    @Autowired
    lateinit var mockMvc: MockMvc
    @Autowired
    lateinit var objectMapper: ObjectMapper

    init {
        beforeSpec {
            // mock bean 스프링 컨텍스트에서 교체 수동 주입
            // mockk(relaxed = true) : 아무 설정 없어도 호출 시 예외 x, 기본값 자동 반환
            every { postService.createPost(any()) } returns mockk(relaxed = true) // 아무 설정 없어도 호출 시 예외 x, 기본값 자동 반환
        }

        describe("게시글 컨트롤러") {
            context("POST /post 요청시") {
                it("200 응답 반환") {
                    val request = PostRequest("제목", "내용")

                    val result = mockMvc.perform(
                        post("/post")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
                    )
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(status().isOk)
                        .andReturn()
                }
            }
        }
    }
}