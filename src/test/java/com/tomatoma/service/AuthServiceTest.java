package com.tomatoma.service;

import com.tomatoma.dto.RegisterRequest;
import com.tomatoma.entity.User;
import com.tomatoma.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import org.springframework.test.context.bean.override.mockito.MockitoBean;


@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    private RegisterRequest validRequest;

    @BeforeEach
    void setUp() {
        validRequest = new RegisterRequest();
        validRequest.setUserId("testuser123");
        validRequest.setPassword("testpass1234");
        validRequest.setEmail("test@example.com");
    }

    @Test
    @DisplayName("정상 회원가입시, 비밀번호 해시 저장 확인")
    void register_success() {
        //given (테스트 환경준비)
        when(userRepository.existsByUserId("testuser123")).thenReturn(false);
        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(passwordEncoder.encode("testpass1234")).thenReturn("HASHED_PASSWORD");
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        //when (실제 동작 실행)
        User result = authService.register(validRequest);

        //then (검증)
        assertThat(result.getUserId()).isEqualTo("testuser123");
        assertThat(result.getEmail()).isEqualTo("test@example.com");
        assertThat(result.getPassword())
                .isEqualTo("HASHED_PASSWORD")
                .isNotEqualTo("testpass1234");

        verify(passwordEncoder).encode("testpass1234");
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("중복 userId - IllegalArgumentException 발생")
    void register_duplicateUserId() {

        when(userRepository.existsByUserId("testuser")).thenReturn(true);

        assertThatThrownBy(() -> authService.register(validRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이미 사용중인 Id");

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("중복 email — IllegalArgumentException 발생")
    void register_duplicateEmail() {
        // Given
        when(userRepository.existsByUserId(anyString())).thenReturn(false);
        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);

        // When & Then
        assertThatThrownBy(() -> authService.register(validRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이미 사용중인 이메일");

        verify(userRepository, never()).save(any(User.class));
    }

}
