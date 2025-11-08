package com.jun.board.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.jun.board.controller.request.PostRequest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
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
    val title: String = "제목"
    val username: String = "작성자"
    val content: String = "내용"

    @BeforeEach
    fun setUp() {
        val request = PostRequest(title, content)

        mockMvc.perform(
            post("/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .param("username", username) // 시큐리티 도입 시, 변경 필요
        )
    }

    @Test
    @DisplayName("POST /post 테스트")
    fun createPost() {
        val request = PostRequest("새로운 제목", "새로운 내용")

        mockMvc.perform(
            post("/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .param("username", "새로운 작성자") // 시큐리티 도입 시, 변경 필요
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect { status().isOk }
            .andReturn()
    }

    @Test
    @DisplayName("GET /post/{id} 테스트")
    fun getPost() {
        mockMvc.perform(
            get("/post/1")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo(MockMvcResultHandlers.print()) // 왜 람다가 아니라 이렇게 바꾸니까 되지...?
            .andExpect { status().isOk }
            .andReturn()
    }

    @Test
    @DisplayName("GET /post 테스트")
    fun getAllPosts() {
        mockMvc.perform(
            get("/post")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect { status().isOk }
            .andReturn()
    }

    @Test
    @DisplayName("PATCH /post/{id} 테스트")
    fun updatePost() {
        val request = PostRequest("바꾸려는 제목", "바꾸려는 내용")

        mockMvc.perform(
            patch("/post/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .param("username", username)
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect { status().isOk }
            .andReturn()
    }

    @Test
    @DisplayName("DELETE /post/{id} 테스트")
    fun deletePost() {
        mockMvc.perform(
            delete("/post/1")
                .contentType(MediaType.APPLICATION_JSON)
                .param("username", username)
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect { status().isOk }
            .andReturn()
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