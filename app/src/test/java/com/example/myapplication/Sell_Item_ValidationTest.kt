package com.example.myapplication

import org.junit.Assert.*

import org.junit.Test

class Sell_Item_ValidationTest {


    //empty title return false
    @Test
    fun `empty title return false`() {

        val sell_Item_Validation = Sell_Item_Validation()

        val result = sell_Item_Validation.sellItemValidationFields(
            "",
            "600",
            "M,L,S",
            "Best Item",

            )
        assertEquals(false, result)
    }

    //empty price return false
    @Test
    fun `empty price return false`() {

        val sell_Item_Validation = Sell_Item_Validation()

        val result = sell_Item_Validation.sellItemValidationFields(
            "Bag",
            "600",
            "M,L,S",
            "Best Item",

            )
        assertEquals(false, result)
    }

    //empty size return false
    @Test
    fun `empty size return false`() {

        val sell_Item_Validation = Sell_Item_Validation()

        val result = sell_Item_Validation.sellItemValidationFields(
            "Bag",
            "6500",
            "",
            "Best Item",
        )
        assertEquals(false, result)
    }

    //empty Description return false
    @Test
    fun `empty Description return false`() {

        val sell_Item_Validation = Sell_Item_Validation()

        val result = sell_Item_Validation.sellItemValidationFields(
            "Bag",
            "6500",
            "L,M,S",
            "",
        )
        assertEquals(false, result)
    }


    //complete all return true
    @Test
    fun `complete all return true`() {

        val sell_Item_Validation = Sell_Item_Validation()

        val result = sell_Item_Validation.sellItemValidationFields(
            "Bag",
            "6500",
            "M,L,S",
            "Best Item",

            )
        assertEquals(false, result)
    }

}