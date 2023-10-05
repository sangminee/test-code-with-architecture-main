package com.example.demo.post.domain;

import com.example.demo.common.service.post.ClockHolder;
import com.example.demo.mock.TestClockHolder;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PostTest {

    @Test
    public void PostCreate으로_게시물을_만들_수_있다(){
        // given
        PostCreate postCreate = PostCreate.builder()
                .writerId(1)
                .content("helloworld")
                .build();
        User writer = User.builder()
                .email("kok202@naver.com")
                .nickname("kok202")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaa-aaaaa-aaaaaa-aaaaaaaaaaaab")
                .build();

        // when
        Post post = Post.from(writer, postCreate, new TestClockHolder(13513515L));

        // then
        assertThat(post.getContent()).isEqualTo("helloworld");
        assertThat(post.getCreatedAt()).isEqualTo(13513515L);
        assertThat(post.getWriter().getEmail()).isEqualTo("kok202@naver.com");
        assertThat(post.getWriter().getNickname()).isEqualTo("kok202");
        assertThat(post.getWriter().getAddress()).isEqualTo("Seoul");
        assertThat(post.getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(post.getWriter().getCertificationCode()).isEqualTo("aaaaa-aaaaa-aaaaaa-aaaaaaaaaaaab");
    }

    @Test
    public void PostUpdate으로_게시물을_수정할_수_있다(){
        // given
        PostUpdate postUpdate = PostUpdate.builder()
                .content("hello world")
                .build();
        User writer = User.builder()
                .email("kok202@naver.com")
                .nickname("kok202")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaa-aaaaa-aaaaaa-aaaaaaaaaaaab")
                .build();

        Post post = Post.builder()
                .id(1L)
                .content("helloworld")
                .createdAt(13513515L)
                .modifiedAt(0L)
                .writer(writer)
                .build();

        // when
        post = post.update(postUpdate, new TestClockHolder(13513515L));

        // then
        assertThat(post.getContent()).isEqualTo("hello world");
        assertThat(post.getCreatedAt()).isEqualTo(13513515L);
        assertThat(post.getWriter().getEmail()).isEqualTo("kok202@naver.com");
        assertThat(post.getWriter().getNickname()).isEqualTo("kok202");
        assertThat(post.getWriter().getAddress()).isEqualTo("Seoul");
        assertThat(post.getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(post.getWriter().getCertificationCode()).isEqualTo("aaaaa-aaaaa-aaaaaa-aaaaaaaaaaaab");
    }
}
