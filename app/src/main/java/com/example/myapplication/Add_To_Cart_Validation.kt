package com.example.myapplication

class Add_To_Cart_Validation {

    fun addToCartValidateFields(testSize: String): Boolean {

        if (testSize.isEmpty()){
            false
        }

        return true
    }
}