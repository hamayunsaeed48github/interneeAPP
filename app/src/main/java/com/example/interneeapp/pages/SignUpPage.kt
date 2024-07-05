package com.example.interneeapp.pages

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.interneeapp.AuthStates
import com.example.interneeapp.AuthViewModel
import com.example.interneeapp.R

@Composable
fun SignUpPage(navController: NavHostController, authViewModel: AuthViewModel){

    var email by remember{
        mutableStateOf("")
    }

    var password by remember{
        mutableStateOf("")
    }
    
    val authState = authViewModel.authStatus.observeAsState()

    val context = LocalContext.current
    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthStates.Authenticated -> navController.navigate("main_screen")
            is AuthStates.Error -> Toast.makeText(context,
                (authState.value as AuthStates.Error).message,Toast.LENGTH_LONG
                ).show()
            else -> Unit
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.internee_logo ),
            contentDescription =null
        )
        Spacer(modifier = Modifier.height(40.dp))
        OutlinedTextField(
            value = email,
            onValueChange = {email = it},
            placeholder = {
                Text(text = "Enter Your Email")
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),

            )
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {password = it},
            placeholder = {
                Text(text = "Enter Your Password")
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),

            )

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                      authViewModel.SignUpMethod(email,password)
            },
            enabled = authState.value != AuthStates.Loading,
            colors = ButtonDefaults.filledTonalButtonColors(
                containerColor = colorResource(id = R.color.internee_color),
                contentColor = colorResource(id = R.color.white)
            ),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(
                text = "SignUp",
                fontSize = 20.sp
            )

        }

        Spacer(modifier = Modifier.height(20.dp))
        Row {
            Text(text = "Already have Account?")
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "SignIn",
                color = colorResource(id = R.color.internee_color ),
                modifier = Modifier.clickable {
                    navController.navigate("SignIn")
                }
            )

        }



    }
}


@Preview(showBackground = true)
@Composable
fun SignUpPagePreview() {
    val navController = rememberNavController()
    val authViewModel = AuthViewModel()
    SignUpPage(navController = navController, authViewModel =authViewModel )
}