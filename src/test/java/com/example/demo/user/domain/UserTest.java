package com.example.demo.user.domain;

import com.example.demo.common.domain.exception.CertificationCodeNotMatchedException;
import com.example.demo.mock.TestClockHolder;
import com.example.demo.mock.TestUuidHolder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UserTest {

    @Test
    public void User는_UserCreate_객체로_생성할_수_있다(){
        // given
        UserCreate userCreate = UserCreate.builder()
                .email("kok202@naver.com")
                .nickname("kok202")
                .address("Pangyo")
                .build();

        // when
        User user = User.from(userCreate, new TestUuidHolder("aaaaa-aaaaa-aaaaaa-aaaaaaaaaaaab"));

        //then
        assertThat(user.getId()).isNull();
        assertThat(user.getEmail()).isEqualTo("kok202@naver.com");
        assertThat(user.getNickname()).isEqualTo("kok202");
        assertThat(user.getAddress()).isEqualTo("Pangyo");
        assertThat(user.getStatus()).isEqualTo(UserStatus.PENDING);
        assertThat(user.getCertificationCode()).isEqualTo("aaaaa-aaaaa-aaaaaa-aaaaaaaaaaaab");
    }

    @Test
    public void User는_UserUpdate_객체로_데이터를_업데이트_할_수_있다(){
        // given
        User user = User.builder()
                .id(1L)
                .email("kok202@kakao.com")
                .nickname("kok202")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(100L)
                .certificationCode("aaaaa-aaaaa-aaaaaa-aaaaaaaaaaaab")
                .build();
        UserUpdate userUpdate = UserUpdate.builder()
                .nickname("kok202-k")
                .address("Pangyo")
                .build();

        // when
        user = user.update(userUpdate);

        //then
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getEmail()).isEqualTo("kok202@kakao.com");
        assertThat(user.getNickname()).isEqualTo("kok202-k");
        assertThat(user.getAddress()).isEqualTo("Pangyo");
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(user.getLastLoginAt()).isEqualTo(100L);
        assertThat(user.getCertificationCode()).isEqualTo("aaaaa-aaaaa-aaaaaa-aaaaaaaaaaaab");
    }

    @Test
    public void User는_로그인을_할_수_있고_로그인시_마지막_로그인_시간이_변경된다(){
        // given
        User user = User.builder()
                .id(1L)
                .email("kok202@kakao.com")
                .nickname("kok202")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(100L)
                .certificationCode("aaaaa-aaaaa-aaaaaa-aaaaaaaaaaaab")
                .build();

        // when
        user = user.login(new TestClockHolder(13513515));

        //then
        assertThat(user.getLastLoginAt()).isEqualTo(13513515);
    }

    @Test
    public void User는_유효한_인증_코드로_계정을_활성화_할_수_있다(){
        // given
        User user = User.builder()
                .id(1L)
                .email("kok202@kakao.com")
                .nickname("kok202")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(100L)
                .certificationCode("aaaaa-aaaaa-aaaaaa-aaaaaaaaaaaab")
                .build();

        // when
        user = user.certificate("aaaaa-aaaaa-aaaaaa-aaaaaaaaaaaab");

        //then
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    public void User는_잘못된_인증_코드로_계정을_활성화_하려하면_에러를_던진다(){
        // given
        User user = User.builder()
                .id(1L)
                .email("kok202@kakao.com")
                .nickname("kok202")
                .address("Seoul")
                .status(UserStatus.PENDING)
                .lastLoginAt(100L)
                .certificationCode("aaaaa-aaaaa-aaaaaa-aaaaaaaaaaaa")
                .build();

        // when
        // then
        assertThatThrownBy(() -> user.certificate("aaaaa-aaaaa-aaaaaa-aaaaaaaaaaaab"))
                .isInstanceOf(CertificationCodeNotMatchedException.class);
    }

}
