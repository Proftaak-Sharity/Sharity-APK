package com.example.sharity_apk.model

data class LoginResponse(val error: Boolean,
                         val message: String,
                         val customer: Customer) {
}