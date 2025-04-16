package com.kanoyatech.snapdex.domain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class UserDataValidatorTest {
    private val validator = UserDataValidator()

    @Nested
    inner class NameValidation {
        @Test
        fun `valid name returns true`() {
            assertTrue(validator.validateName("Ash Ketchum"))
        }

        @Test
        fun `blank name returns false`() {
            assertFalse(validator.validateName("   "))
        }
    }

    @Nested
    inner class EmailValidation {
        @ParameterizedTest
        @ValueSource(
            strings =
                [
                    "ash@poke.com",
                    "misty.water@kanto.org",
                    "brock@pewter.city",
                    "team-rocket_01@rocket.co",
                    "prof.oak@pal.labs",
                ]
        )
        fun `valid emails are accepted`(email: String) {
            assertTrue(validator.validateEmail(email), "Expected valid email: $email")
        }

        @ParameterizedTest
        @ValueSource(
            strings =
                [
                    "ashpoke.com",
                    "misty@",
                    "@kanto.com",
                    "brock@.com",
                    "",
                    "invalid@characters!",
                    "hello@domain,com",
                ]
        )
        fun `invalid emails are rejected`(email: String) {
            assertFalse(validator.validateEmail(email), "Expected invalid email: $email")
        }
    }

    @Nested
    inner class PasswordValidation {
        @Test
        fun `valid password passes all checks`() {
            val result = validator.validatePassword("Abcdefg9Z")
            assertTrue(result.hasMinLength)
            assertTrue(result.hasDigit)
            assertTrue(result.hasLowercase)
            assertTrue(result.hasUppercase)
        }

        @Test
        fun `password too short fails min length`() {
            val result = validator.validatePassword("Ab9c")
            assertFalse(result.hasMinLength)
            assertTrue(result.hasDigit)
            assertTrue(result.hasLowercase)
            assertTrue(result.hasUppercase)
        }

        @Test
        fun `password with no digit fails digit check`() {
            val result = validator.validatePassword("AbcdefghZ")
            assertFalse(result.hasDigit)
        }

        @Test
        fun `password with no uppercase fails uppercase check`() {
            val result = validator.validatePassword("abcdefg9")
            assertFalse(result.hasUppercase)
        }

        @Test
        fun `password with no lowercase fails lowercase check`() {
            val result = validator.validatePassword("ABCDEFG9")
            assertFalse(result.hasLowercase)
        }
    }
}
