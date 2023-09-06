package com.example.demo.repository;

import com.example.demo.model.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest(showSql = true)
@TestPropertySource("classpath:test-application.properties")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void UserRepository_가_제대로_연결되었다(){
        // given
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("abc@gamil.com");
        userEntity.setAddress("Seoul");
        userEntity.setNickname("kok202");
        userEntity.setStatus(UserStatus.ACTIVE);
        userEntity.setCertificationCode("aaa-aaa-aaa-aaa-aaa");
        
        // when
        UserEntity result = userRepository.save(userEntity);

        // then
        assertThat(result.getId()).isNotNull();
    }

    @Test
    void findByIdAndStatus_로_유저_데이터를_찾아올_수_있다(){

    }

}
