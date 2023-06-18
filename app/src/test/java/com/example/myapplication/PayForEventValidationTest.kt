package com.example.myapplication

import org.junit.Assert.*

import org.junit.Test

class PayForEventValidationTest {

    @Test
    fun `empty event name returns false`() {

        val payForEventValidation = PayForEventValidation()

        val result = payForEventValidation.payForEvent(
            "",
            200,
            2500
        )

        assertEquals(false, result)
    }

    @Test
    fun `empty event amount returns false`() {

        val payForEventValidation = PayForEventValidation()

        val result = payForEventValidation.payForEvent(
            "Lecture 06",
            200,
            2500
        )

        assertEquals(false, result)
    }

    @Test
    fun `amount lower than totalBalance returns true`() {

        val payForEventValidation = PayForEventValidation()

        val result = payForEventValidation.payForEvent(
            "Lecture 06",
            200,
            2500
        )

        assertEquals(true, result)
    }

    @Test
    fun `amount higher than totalBalance returns false`() {

        val payForEventValidation = PayForEventValidation()

        val result = payForEventValidation.payForEvent(
            "Lecture 06",
            3000,
            2500
        )

        assertEquals(false, result)
    }
}