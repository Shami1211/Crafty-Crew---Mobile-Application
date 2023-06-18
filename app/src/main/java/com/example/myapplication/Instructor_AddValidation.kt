package com.example.myapplication

class Instructor_AddValidation {
    fun instructorAddValidateFields(testName_of_Instructor: String, testEmail: String, testUser_Name:String, testPassword: String): Boolean {


        if (testName_of_Instructor.isEmpty()) {
            return false
        }

        if (testEmail.isEmpty()) {
            return false
        }

        if (testUser_Name.isEmpty()) {
            return false
        }

        if (testPassword.isEmpty()) {
            return false
        }

        return true
    }

}