package com.yourlhub.utils;

import com.yourlhub.domain.exceptions.GraphqlException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class JwtTokenUtilTest {

    JwtTokenUtil jwtTokenUtil;

    @BeforeEach
    void setUp() {
        jwtTokenUtil = new JwtTokenUtil();
        ReflectionTestUtils.setField(jwtTokenUtil, "SECRET_KEY", "yourlHubjwt1998samplejwtsecretthisisapenyyyyy");
        ReflectionTestUtils.setField(jwtTokenUtil, "JWT_ISSUER", "yourlhub");
    }

    @Nested
    class generateTokenのテスト {
        @Test
        @DisplayName("UserIdが渡された時、例外が発生しない")
        void generateTokenTest1() {
            System.out.println(jwtTokenUtil.generateToken("1125abfc-9207-4e51-bd4f-9aaa73d9a65d"));
            assertDoesNotThrow(() -> jwtTokenUtil.generateToken("1125abfc-9207-4e51-bd4f-9aaa73d9a65d"));
        }


        @Test
        @DisplayName("何かしら例外が発生した際にはGraphqlExceptionが返却される")
        void generateTokenTest2() {
            ReflectionTestUtils.setField(jwtTokenUtil, "SECRET_KEY", null);

            assertThrows(GraphqlException.class, () -> jwtTokenUtil.generateToken(null));
        }
    }

    @Nested
    class getUserIdFromTokenのテスト {
        @Test
        @DisplayName("正常なtokenが渡された時、userIDが返却される")
        void getUserIdFromTokenTest1() {

            // NOTE tokenの有効期限を999年のものに設定し、常にテストが通るように擬似的に実現している。
            final String userId = jwtTokenUtil.getUserIdFromToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMTI1YWJmYy05MjA3LTRlNTEtYmQ0Zi05YWFhNzNkOWE2NWQiLCJpc3MiOiJ5b3VybGh1YiIsImlhdCI6MTY5ODQxNDM4MCwiZXhwIjoxNjk5MjkzMjY0fQ.SItJrCIVujQAd6w_CtJS4ag5STFkN8alh9EXtwljq1s");
            assertEquals("1125abfc-9207-4e51-bd4f-9aaa73d9a65d", userId);

        }


        @Test
        @DisplayName("異常なtokenが渡された時、空文字を返却する")
        void getUserIdFromTokenTest2() {
            final String userId = jwtTokenUtil.getUserIdFromToken("invalid user id");
            assertEquals("", userId);
        }
    }
}