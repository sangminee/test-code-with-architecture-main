package com.example.demo.post.service;

import com.example.demo.mock.FakePostRepository;
import com.example.demo.mock.FakeUserRepository;
import com.example.demo.mock.TestClockHolder;
import com.example.demo.post.domain.Post;
import com.example.demo.post.domain.PostCreate;
import com.example.demo.post.domain.PostUpdate;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PostServiceTest {

    private PostService postService;

    @BeforeEach
    void init(){
        FakePostRepository fakePostRepository = new FakePostRepository();
        FakeUserRepository fakeUserRepository = new FakeUserRepository();

        this.postService = PostService.builder()
                .postRepository(fakePostRepository)
                .userRepository(fakeUserRepository) // 테스트를 짜는 것이 귀찮으면 위존성을 줄여라
                .clockHolder(new TestClockHolder(13513515L))
                .build();

        User user1 = User.builder()
                .id(1L)
                .email("kok202@naver.com")
                .nickname("kok202")
                .address("Seoul")
                .certificationCode("aaa-aaa-aaa-aaa-aaa")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(0L)
                .build();
        User user2 = User.builder()
                .id(2L)
                .email("kok303@naver.com")
                .nickname("kok303")
                .address("Seoul")
                .certificationCode("aaa-aaa-aaa-aaa-aab")
                .status(UserStatus.PENDING)
                .lastLoginAt(0L)
                .build();

        fakeUserRepository.save(user1);
        fakeUserRepository.save(user2);

        fakePostRepository.save(Post.builder()
                        .id(1L)
                        .content("helloworld")
                        .createdAt(13513515L)
                        .modifiedAt(0L)
                        .writer(user1)
                        .build());
    }

    @Test
    void getById는_존재하는_게시물을_내려준다(){
        // given
        // when
        Post result = postService.getById(1);

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
        Post result = postService.create(postCreate);

        // then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getContent()).isEqualTo("hihi");
        assertThat(result.getWriter().getEmail()).isEqualTo("kok202@naver.com");
        assertThat(result.getCreatedAt()).isEqualTo(13513515L);
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
        Post post = postService.getById(1);
        assertThat(post.getContent()).isEqualTo("hello world");
        assertThat(post.getModifiedAt()).isEqualTo(13513515L);
    }

}
