package com.example.myapplication

class Sell_Item_Validation {

    fun sellItemValidationFields(testSelItemTitle: String, testSelItemPrice: String, testSelItemSize:String, testSelItemDescription: String): Boolean {

        //Validation Part
        if (testSelItemTitle.isEmpty()) {
            return false
        }

        if (testSelItemPrice.isEmpty()) {
            return false
        }

        if (testSelItemSize.isEmpty()) {
            return false
        }

        if (testSelItemDescription.isEmpty()) {
            return false
        }

        return true
    }

}


