package com.yourlhub.interfaces.database;

import com.yourlhub.domain.enums.UserStatus;
import com.yourlhub.domain.models.User;
import org.junit.jupiter.api.*;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NamedParameterJdbcOperations jdbcOperations;

    @BeforeEach
    void setUp() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @AfterEach
    void tearDown() {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(
                "DELETE FROM users WHERE id = CAST('1325abfc-9207-4e51-bd4f-9aaa73d9a65d' AS UUID)",
                null, keyHolder);
    }

    @Nested
    class findByIdのテスト {

        @BeforeEach
        void setUp() {
            final User user =
                    new User(
                            "1325abfc-9207-4e51-bd4f-9aaa73d9a65d",
                            "example@example.com",
                            "first",
                            "last",
                            "nick",
                            passwordEncoder.encode("Defaultp@ss1"),
                            true,
                            1);
            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcOperations.update(
                    "INSERT INTO users (" +
                            "id, email, first_name, last_name, nick_name, password_digest, activated, status, created_at, updated_at" +
                            ") VALUES(" +
                            "CAST(:id AS UUID), :email, :firstName, :lastName, :nickName, :passwordDigest, :activated, :status, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP" +
                            ")",
                    new BeanPropertySqlParameterSource(user), keyHolder);
        }



        @Test
        @DisplayName("存在するUserIdが渡された時、Userを取得する")
        void findById1() {
            final User user = userRepository.findById("1325abfc-9207-4e51-bd4f-9aaa73d9a65d");
            assertEquals("1325abfc-9207-4e51-bd4f-9aaa73d9a65d", user.getId());
            assertEquals("example@example.com", user.getEmail());
            assertEquals("first", user.getFirstName());
            assertEquals("last", user.getLastName());
            assertEquals("nick", user.getNickName());
            assertEquals(true, user.getActivated());
            assertEquals(UserStatus.VALID.getValue(), user.getStatus());

        }

        @Test
        @DisplayName("存在しないUserIdが渡された時、Userを取得しない")
        void findById2() {
            final User user = userRepository.findById("9325abfc-9207-4e61-bd4f-9aaa73d9a65d");
            assertNull(user);
        }
    }

    @Nested
    class findByEmailのテスト {

        @BeforeEach
        void setUp() {
            final User user =
                    new User(
                            "1325abfc-9207-4e51-bd4f-9aaa73d9a65d",
                            "example@example.com",
                            "first",
                            "last",
                            "nick",
                            passwordEncoder.encode("Defaultp@ss1"),
                            true,
                            1);
            GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcOperations.update(
                    "INSERT INTO users (" +
                            "id, email, first_name, last_name, nick_name, password_digest, activated, status, created_at, updated_at" +
                            ") VALUES(" +
                            "CAST(:id AS UUID), :email, :firstName, :lastName, :nickName, :passwordDigest, :activated, :status, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP" +
                            ")",
                    new BeanPropertySqlParameterSource(user), keyHolder);
        }



        @Test
        @DisplayName("存在するemailが渡された時、Userを取得する")
        void findByEmailTest1() {
            final User user = userRepository.findByEmail("example@example.com");
            assertEquals("1325abfc-9207-4e51-bd4f-9aaa73d9a65d", user.getId());
            assertEquals("example@example.com", user.getEmail());
            assertEquals("first", user.getFirstName());
            assertEquals("last", user.getLastName());
            assertEquals("nick", user.getNickName());
            assertEquals(true, user.getActivated());
            assertEquals(UserStatus.VALID.getValue(), user.getStatus());

        }

        @Test
        @DisplayName("存在しないemailが渡された時、Userを取得しない")
        void findByEmailTest2() {
            final User user = userRepository.findByEmail("xxx@xxx.com");
            assertNull(user);
        }
    }
}