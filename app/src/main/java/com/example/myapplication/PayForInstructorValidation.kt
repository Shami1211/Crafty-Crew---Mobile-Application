package com.example.myapplication

class PayForInstructorValidation {

    fun payForInstructor(availableAmount: Int, payAmount: Int): Boolean {

        if (payAmount == 0){
            return false
        }

        if (availableAmount < payAmount){
            return false
        }

        return true
    }
}