package com.example.myapplication

class AddEventValidation {

    fun addEventValidateFields(testTitle: String, testPlace: String, testDate:String, testTime: String): Boolean {


        if (testTitle.isEmpty()) {
            return false
        }

        if (testPlace.isEmpty()) {
            return false
        }

        if (testDate.isEmpty()) {
            return false
        }

        if (testTime.isEmpty()) {
            return false
        }

        return true
    }
}