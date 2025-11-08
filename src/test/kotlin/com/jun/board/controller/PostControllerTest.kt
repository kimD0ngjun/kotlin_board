package com.jun.board.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.jun.board.controller.request.PostRequest
import com.jun.board.controller.response.PostResponse
import com.jun.board.service.PostService
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

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

        // 응답 JSON 문자열 가져오기
        val responseJson = result.response.contentAsString

        // ObjectMapper로 PostResponse 타입으로 변환
        val response = objectMapper.readValue(responseJson, PostResponse::class.java)

        // Assertion
        Assertions.assertNotNull(response) // null 아님 검증
        Assertions.assertTrue(response is PostResponse) // 타입 검증


    }
}

/**
 * kotest는 리플렉션으로 Spec()을 생성
 * 그래서 기본 생성자를 무조건 요구함
 * 근데 컨트롤러 테스트는 내 코드 말고도 mvc나 오브젝트 매퍼 등 부수적인 것들 의존성 주입이 요구됨
 * lateinit을 하자니 초기화 정합성 예외가 터지고, 그렇다고 의존성 주입을 하자니 기본생성자가 없어서 안 되고
 *
 * 가능한 방법이 없으려나? 컨트롤러 테스트 같은 경우는 kotest가 부적합한 경우려나?
 */

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