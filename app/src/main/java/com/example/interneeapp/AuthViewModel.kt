package com.example.interneeapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class AuthViewModel:ViewModel() {

val auth : FirebaseAuth = FirebaseAuth.getInstance()
    private val _autStatus = MutableLiveData<AuthStates>()
    val authStatus : LiveData<AuthStates> = _autStatus

    init {
        checkAuthStatus()
    }

    fun checkAuthStatus(){
        if(auth.currentUser == null){
            _autStatus.value = AuthStates.Unauthenticated
        }else{
            _autStatus.value = AuthStates.Authenticated
        }
    }

    fun SignInMethod(email:String,password:String){
        if (email.isEmpty() || password.isEmpty()){
            _autStatus.value = AuthStates.Error("Email and password can't be Empty")
            return
        }

        _autStatus.value = AuthStates.Loading
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener{task->
                if (task.isSuccessful){
                    _autStatus.value = AuthStates.Authenticated
                }else{
                    _autStatus.value = AuthStates.Error(task.exception?.message?:"Something went wrong")
                }

            }
    }

    fun SignUpMethod(email:String,password:String){
        if (email.isEmpty() || password.isEmpty()){
            _autStatus.value = AuthStates.Error("Email and password can't be Empty")
            return
        }

        _autStatus.value = AuthStates.Loading
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener{task->
                if (task.isSuccessful){
                    _autStatus.value = AuthStates.Authenticated
                }else{
                    _autStatus.value = AuthStates.Error(task.exception?.message?:"Something went wrong")
                }

            }
    }

    fun SignOutMethod(){
        auth.signOut()
        _autStatus.value = AuthStates.Unauthenticated
    }
}