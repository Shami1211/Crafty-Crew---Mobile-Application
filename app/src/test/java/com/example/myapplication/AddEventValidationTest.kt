package com.example.myapplication

import org.junit.Assert.*

import org.junit.Test

class AddEventValidationTest {

    @Test
    fun `empty title return false`() {

        val addEventValidation = AddEventValidation()

        val result = addEventValidation.addEventValidateFields(
            "",
            "Hotel King",
            "2023/02/08",
            "9 PM"
        )
        assertEquals(false, result)
    }

    @Test
    fun `empty date return false`() {

        val addEventValidation = AddEventValidation()

        val result = addEventValidation.addEventValidateFields(
            "Lecture 5",
            "Hotel King",
            "",
            "9 PM"
        )
        assertEquals(false, result)
    }

    @Test
    fun `empty all return false`() {

        val addEventValidation = AddEventValidation()

        val result = addEventValidation.addEventValidateFields(
            "",
            "",
            "",
            ""
        )
        assertEquals(false, result)
    }

    @Test
    fun `complete all return true`() {

        val addEventValidation = AddEventValidation()

        val result = addEventValidation.addEventValidateFields(
            "Lecture 5",
            "Hotel King",
            "2023/02/08",
            "9 PM"
        )
        assertEquals(true, result)
    }
}