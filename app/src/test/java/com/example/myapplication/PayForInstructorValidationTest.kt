package com.example.myapplication

import org.junit.Assert.*

import org.junit.Test

class PayForInstructorValidationTest {

    @Test
    fun payForInstructor() {

        val payForInstructorValidation = PayForInstructorValidation()

        val result = payForInstructorValidation.payForInstructor(
            100,
            100
        )

        assertEquals(true, result)
    }

    @Test
    fun payForInstructorInvalid() {

        val payForInstructorValidation = PayForInstructorValidation()

        val result = payForInstructorValidation.payForInstructor(
            1600,
            1800
        )

        assertEquals(false, result)
    }


}