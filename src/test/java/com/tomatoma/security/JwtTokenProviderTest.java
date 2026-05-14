package com.tomatoma.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        jwtTokenProvider = new JwtTokenProvider();
        // @Value 주입이 안 되니까 ReflectionTestUtils로 직접 세팅
        ReflectionTestUtils.setField(
                jwtTokenProvider,
                "secretString",
                "TestSecretKeyForJunitTestsNeedsToBeAtLeast32BytesLong"
        );
        ReflectionTestUtils.setField(
                jwtTokenProvider,
                "accessTokenExpirationMs",
                1800000L
        );
        jwtTokenProvider.init();
    }

    @Test
    @DisplayName("토큰 생성 → 검증 → userId 추출")
    void createAndParseToken() {
        String token = jwtTokenProvider.createAccessToken("alice", "USER");

        assertThat(token).isNotBlank();
        assertThat(token.split("\\.")).hasSize(3);   // header.payload.signature

        assertThat(jwtTokenProvider.validateToken(token)).isTrue();
        assertThat(jwtTokenProvider.getUserIdFromToken(token)).isEqualTo("alice");
        assertThat(jwtTokenProvider.getRoleFromToken(token)).isEqualTo("USER");
    }

    @Test
    @DisplayName("위조된 토큰은 invalid")
    void tamperedTokenIsInvalid() {
        String token = jwtTokenProvider.createAccessToken("alice", "USER");

        // 마지막 글자 하나 바꿔서 시그니처 깨기
        String tampered = token.substring(0, token.length() - 1) + "X";

        assertThat(jwtTokenProvider.validateToken(tampered)).isFalse();
    }

    @Test
    @DisplayName("형식이 깨진 토큰은 invalid")
    void malformedTokenIsInvalid() {
        assertThat(jwtTokenProvider.validateToken("not.a.token")).isFalse();
        assertThat(jwtTokenProvider.validateToken("")).isFalse();
    }
}