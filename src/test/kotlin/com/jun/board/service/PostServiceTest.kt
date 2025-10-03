package com.jun.board.service

import com.jun.board.domain.dto.PostDTO
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class PostServiceTest @Autowired constructor(
    // 해결책 2) 코틀린식 생성자 주입
    private val postService: PostService
) {

    /**
     * 붉은 줄이 뜨는 이유는 코틀린과 자바의 null 취급 때문
     * 자바는 NPE 위험이 있어도 컴파일에서 이를 막지 않아서 그냥 빈 필드로 둘 수 있음
     * 코틀린은 null safety를 강제하므로 아래와 같은 케이스는 불가능
     */
//    val postService: PostService
    /**
     * 해결책 1) 지금은 초기화 안 했지만, 런타임에 꼭 초기화하는 방법
     * 그렇다고 별도의 초기화 로직은 필요없고 스프링이 빈 자동 주입해줌
     * 빈 주입 실패시, UninitializedPropertyAccessException 발생
     * 생성자 주입보다는 안정성이 떨어짐
     */
//    lateinit var postServiceTest: PostServiceTest

    @Test
    @DisplayName("게시글 생성 테스트")
    fun createPost() {
        val dto1 = PostDTO(
            null,
            "임시 제목1",
            "임시 사용자1",
            "임시로 작성한 글입니다111"
        )
        val dto2 = PostDTO(
            null,
            "임시 제목2",
            "임시 사용자2",
            "임시로 작성한 글입니다2222"
        )

        val createPost1 = postService.createPost(dto1)
        val createPost2 = postService.createPost(dto2)

        assertEquals(1L, createPost1.id)
        assertEquals(2L, createPost2.id)
    }

    @Test
    fun getPost() {
    }

    @Test
    fun getAllPosts() {
    }

    @Test
    fun updatePost() {
    }

    @Test
    fun deletePost() {
    }

}