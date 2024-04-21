package com.example.loginauthapi.repositories;

import com.example.loginauthapi.domain.user.User;
import com.example.loginauthapi.dtos.auth.RegisterRequestBodyDTO;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should find user by email in DB with success.")
    void findByEmailWithSuccess() {
        String email = "test@gmail.com";

        RegisterRequestBodyDTO createUserDTO = new RegisterRequestBodyDTO("Arlindo Cruz", email, "arlindocruz01");

        this.createUser(createUserDTO);

        Optional<User> foundUser = this.userRepository.findByEmail(email);

        assertThat(foundUser.isPresent()).isTrue();
        assertThat(foundUser.get().getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("Should NOT find user by email in DB.")
    void findByEmailWithError() {
        String email = "noemail@gmail.com";

        Optional<User> foundUser = this.userRepository.findByEmail(email);

        assertThat(foundUser.isPresent()).isFalse();
    }

    private void createUser(RegisterRequestBodyDTO registerRequestBodyDTO) {
        User newUser = new User();

        newUser.setName(registerRequestBodyDTO.name());
        newUser.setEmail(registerRequestBodyDTO.email());
        newUser.setPassword(registerRequestBodyDTO.password());

        this.entityManager.persist(newUser);
    }
}