package com.example.myapplication

import org.junit.Assert.*

import org.junit.Test

class Add_To_Cart_ValidationTest {

    @Test
    fun `empty size return false`() {

        val addToCartValidation = Add_To_Cart_Validation()

        val result = addToCartValidation.addToCartValidateFields(
            ""
        )
        assertEquals(false,result)
    }

    @Test
    fun `size return true`() {

        val addToCartValidation = Add_To_Cart_Validation()

        val result = addToCartValidation.addToCartValidateFields(
            "Medium"
        )
        assertEquals(true,result)
    }
}