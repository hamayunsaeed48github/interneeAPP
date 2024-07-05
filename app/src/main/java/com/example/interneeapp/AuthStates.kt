package com.example.interneeapp

sealed class AuthStates {
    object Authenticated :AuthStates()
    object Unauthenticated:AuthStates()
    object Loading:AuthStates()
    data class Error(val message:String):AuthStates()
}