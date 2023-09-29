package com.example.demo.user.infrastructure;

import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest(showSql = true)
@TestPropertySource("classpath:test-application.properties")
@Sql("/sql/user-repository-test-data.sql") // 테스트 전에 값을 미리 준비해서 테스트할 때 준비된 값을 사용
public class UserJpaRepositoryTest {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Test
    void findByIdAndStatus_로_유저_데이터를_찾아올_수_있다(){
        // given
        // when
        Optional<UserEntity> result = userJpaRepository.findByIdAndStatus(1, UserStatus.ACTIVE);

        // then
        assertThat(result.isPresent()).isTrue();
    }

    @Test
    void findByIdAndStatus_로_데이터가_없으면_Optional_empty_를_내려준다(){
        // given
        // when
        Optional<UserEntity> result = userJpaRepository.findByIdAndStatus(1, UserStatus.PENDING);

        // then
        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    void findByEmailAndStatus_로_유저_데이터를_찾아올_수_있다(){
        // given
        // when
        Optional<UserEntity> result = userJpaRepository.findByEmailAndStatus("kok202@naver.com", UserStatus.ACTIVE);

        // then
        assertThat(result.isPresent()).isTrue();
    }

    @Test
    void findByEmailAndStatus_로_데이터가_없으면_Optional_empty_를_내려준다(){
        // given
        // when
        Optional<UserEntity> result = userJpaRepository.findByEmailAndStatus("abc@gamil.com", UserStatus.PENDING);

        // then
        assertThat(result.isEmpty()).isTrue();
    }

}
