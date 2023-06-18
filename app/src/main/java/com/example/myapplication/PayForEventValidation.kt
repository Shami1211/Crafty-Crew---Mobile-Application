package com.example.myapplication

class PayForEventValidation {

    fun payForEvent(eventName: String, amount: Int, totalBalance: Int): Boolean {

        if (eventName.isEmpty()){
            return false
        }
        if (amount == 0){
            return false
        }
        if (amount > totalBalance){
            return false
        }
        return true
    }
}
