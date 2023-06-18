package com.example.myapplication

import org.junit.Assert.*

import org.junit.Test

class Instructor_AddValidationTest {

    @Test
    fun `empty name return false`() {

        val instructor_AddValidation = Instructor_AddValidation()

        val result = instructor_AddValidation.instructorAddValidateFields(
            "",
            "Ishuwara@gmail.com",
            "ishu",
            "123"

        )
        assertEquals(false, result)
    }

    @Test
    fun `empty email return false`() {

        val instructor_AddValidation = Instructor_AddValidation()

        val result = instructor_AddValidation.instructorAddValidateFields(
            "Ishuwara",
            "",
            "ishu",
            "123"

        )
        assertEquals(false, result)
    }
    @Test
    fun `empty username return false`() {

        val instructor_AddValidation = Instructor_AddValidation()

        val result = instructor_AddValidation.instructorAddValidateFields(
            "Ishuwara",
            "Ishuwara@gmail.com",
            "",
            "123"

        )
        assertEquals(false, result)
    }
    @Test
    fun `empty password return false`() {

        val instructor_AddValidation = Instructor_AddValidation()

        val result = instructor_AddValidation.instructorAddValidateFields(
            "Ishuwara",
            "Ishuwara@gmail.com",
            "ishu",
            ""

        )
        assertEquals(false, result)
    }

    @Test
    fun `empty allemty return false`() {

        val instructor_AddValidation = Instructor_AddValidation()

        val result = instructor_AddValidation.instructorAddValidateFields(
            "",
            "",
            "",
            ""

        )
        assertEquals(false, result)
    }

    @Test
    fun `empty all return true`() {

        val instructor_AddValidation = Instructor_AddValidation()

        val result = instructor_AddValidation.instructorAddValidateFields(
            "Ishuwara",
            "Ishuwara@gmail.com",
            "ishu",
            "123"

        )
        assertEquals(true, result)
    }

}