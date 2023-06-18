package com.example.myapplication

class CardDetailsValidation {

    fun cardFiledValidation(testCardName:String, testCardNumber: String, testCardDate: String, testCardCVV:String): Boolean {

        if (testCardName.isEmpty()){
            return false
        }

        if (testCardNumber.isEmpty()){
            return false
        }

        if (testCardDate.isEmpty()){
            return false
        }

        if (testCardCVV.isEmpty()){
            return false
        }

        return true
    }
}