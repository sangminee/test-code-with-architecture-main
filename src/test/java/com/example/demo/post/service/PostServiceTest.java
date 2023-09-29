package com.example.demo.post.service;

import com.example.demo.post.domain.PostCreate;
import com.example.demo.post.domain.PostUpdate;
import com.example.demo.post.infrastructure.PostEntity;
import com.example.demo.post.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest // 스프링 부트의 의존성을 받는 
@TestPropertySource("classpath:test-application.properties")
@SqlGroup({
        @Sql(value = "/sql/post-service-test-data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
})
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @Test
    void getById는_존재하는_게시물을_내려준다(){
        // given
        // when
        PostEntity result = postService.getById(1);

        // then
        assertThat(result.getContent()).isEqualTo("helloworld");
        assertThat(result.getWriter().getEmail()).isEqualTo("kok202@naver.com");
    }

    // create
    @Test
    void postCreateDto_를_이용하여_게시글을_생성할_수_있다(){
        // given
        PostCreate postCreate = PostCreate.builder()
                .writerId(1)
                .content("hihi")
                .build();

        // when
        PostEntity result = postService.create(postCreate);
        // then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getContent()).isEqualTo("hihi");
        assertThat(result.getWriter().getEmail()).isEqualTo("kok202@naver.com");
        assertThat(result.getCreatedAt()).isGreaterThan(0);
        // assertThat(result.getCertificationCode()).isEqualTo(UserStatus.PENDING);
    }

    @Test
    void postUpdateDto_를_이용하여_게시글을_수정할_수_있다(){
        // given
        PostUpdate postUpdate = PostUpdate.builder()
                .content("hello world")
                .build();

        // when
        postService.update(1, postUpdate);

        // then
        PostEntity postEntity = postService.getById(1);
        assertThat(postEntity.getContent()).isEqualTo("hello world");
        assertThat(postEntity.getModifiedAt()).isGreaterThan(0);
    }

}
