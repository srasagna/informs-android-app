package com.example.iorms

import android.util.Log
import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.iorms.ui.theme.poppinsFontFamily
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isEmailValid by remember { mutableStateOf(false) }
    var isPasswordValid by remember { mutableStateOf(false) }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isLoginClicked by remember { mutableStateOf(false) }
    var isCredentialsInvalid by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .background(Color.White)
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Image(
                painter = painterResource(id = R.drawable.your_app_logo),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 20.dp)
                    .width(120.dp)
            )

            Column(
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                listOf(
                    "Inf" to "omerica",
                    "O" to "ffshore",
                    "R" to "esource",
                    "M" to "anagement",
                    "S" to "ystem"
                ).forEach { (first, last) ->
                    Text(
                        text = annotatedStringTitle(firstWord = first, lastWord = last),
                        fontSize = 24.sp,
                        fontFamily = poppinsFontFamily,
                        color = Color.Black,
                        modifier = Modifier.padding(top = 5.dp)
                    )
                }
            }

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .wrapContentHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = annotatedStringTitle(firstWord = "", lastWord = "INFORMS"),
                        fontSize = 35.sp,
                        fontFamily = poppinsFontFamily,
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                        modifier = Modifier.padding(start = 20.dp, top = 85.dp)
                    )

                    Divider(
                        color = Color.Red,
                        thickness = 3.dp,
                        modifier = Modifier
                            .padding(start = 20.dp)
                            .fillMaxWidth(0.8f)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 10.dp)
                    .fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    TextField(
                        value = email,
                        onValueChange = { newEmail ->
                            email = newEmail
                            isEmailValid = isValidEmail(newEmail)
                        },
                        modifier = Modifier
                            .padding(top = 10.dp, start = 50.dp)
                            .fillMaxWidth(),
                        placeholder = {
                            Text(text = "Email", color = Color.Gray, fontFamily = poppinsFontFamily)
                        },
                        singleLine = true,
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.Transparent,
                            textColor = Color.Black
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        isError = (!isEmailValid || (isLoginClicked && email.isEmpty()))
                    )
                    if (isLoginClicked && email.isEmpty()) {
                        Text(
                            text = "Enter Email",
                            color = Color.Red,
                            modifier = Modifier.padding(top = 2.dp, start = 50.dp)
                        )
                    }
                    if (isLoginClicked && email.isNotEmpty() && !isEmailValid) {
                        Text(
                            text = "Invalid email",
                            color = Color.Red,
                            modifier = Modifier.padding(top = 2.dp, start = 50.dp)
                        )
                    }

                    TextField(
                        value = password,
                        onValueChange = { newPassword ->
                            password = newPassword
                            isPasswordValid = newPassword.length >= 6
                        },
                        trailingIcon = {
                            val image: ImageVector = if (isPasswordVisible) {
                                Icons.Filled.Visibility
                            } else {
                                Icons.Filled.VisibilityOff
                            }
                            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                                Icon(imageVector = image, contentDescription = null)
                            }
                        },
                        modifier = Modifier
                            .padding(top = 5.dp, start = 50.dp)
                            .fillMaxWidth(),
                        placeholder = {
                            Text(text = "Password", color = Color.Gray, fontFamily = poppinsFontFamily)
                        },
                        singleLine = true,
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color.Transparent,
                            textColor = Color.Black
                        ),
                        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        isError = !isPasswordValid
                    )
                    if (isLoginClicked && password.isEmpty()) {
                        Text(
                            text = "Enter Password",
                            color = Color.Red,
                            modifier = Modifier.padding(top = 2.dp, start = 50.dp)
                        )
                    }
                    if (isLoginClicked) {
                        if (password.isEmpty()) {
                            Text(
                                text = "",
                                color = Color.Red,
                                modifier = Modifier.padding(top = 5.dp, start = 50.dp)
                            )
                        } else if (password.isNotEmpty() && !isPasswordValid) {
                            Text(
                                text = "Password must be at least 6 characters",
                                color = Color.Red,
                                modifier = Modifier.padding(top = 5.dp, start = 50.dp)
                            )
                        } else if (isCredentialsInvalid) {
                            Text(
                                text = "Invalid email or password",
                                color = Color.Red,
                                modifier = Modifier.padding(top = 5.dp, start = 50.dp)
                            )
                        }
                    }

                    Button(
                        onClick = {
                            isLoginClicked = true
                            if (email.isNotEmpty() && password.isNotEmpty() && isEmailValid && isPasswordValid) {
                                performLogin(email, password, navController, onLoginFailed = {
                                    isCredentialsInvalid = true
                                })
                            }
                        },
                        modifier = Modifier
                            .padding(vertical = 10.dp)
                            .align(Alignment.CenterHorizontally)
                            .width(140.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text(
                            text = "Login",
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 20.sp,
                            color = Color.White
                        )
                    }
                }
            }

           // Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

fun isValidEmail(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

@Composable
fun annotatedStringTitle(firstWord: String, lastWord: String): AnnotatedString {
    return buildAnnotatedString {
        withStyle(SpanStyle(color = Color.Red)) {
            append(firstWord)
        }
        append(lastWord)
    }
}

fun performLogin(
    email: String,
    password: String,
    navController: NavController,
    onLoginFailed: () -> Unit
) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val userResponse = ApiClient.providesApiService().login(LoginForm(email, password))

            if (userResponse.isSuccessful) {
                val apiResponseBody = userResponse.body()
                if (apiResponseBody != null) {
                    Log.e("test", apiResponseBody.toString())
                    val user = apiResponseBody.loginResponse
                    val resourceId = user.Resource_Id ?: "defaultResourceId"
                    val firstName = user.FirstName ?: "PMC"
                    val role = user.RoleName
                    val selectedClient = "INFOMERICA"
                    val gson = Gson()
                    val apiResponseBodyJson = gson.toJson(apiResponseBody)
                    withContext(Dispatchers.Main) {
                        when (role) {


                                "PMC", "DM" -> navController.navigate("PmcMainScreen/$resourceId/$firstName/$role/employee/$selectedClient/$apiResponseBodyJson")
                            "TEAM MEMBER", "TEAM LEAD" -> navController.navigate("MainScr/$resourceId")
                            else -> {
                                onLoginFailed()
                            }
                        }
                    }
                } else {
                    Log.e("ResponseError", "API response body is null")
                    onLoginFailed()
                }
            } else {
                Log.e("ResponseError", "API response was not successful: ${userResponse.code()}")
                onLoginFailed()
            }
        } catch (e: Exception) {
            Log.e("APIError", "An error occurred during the API call: ${e.message}")
            onLoginFailed()
        }
    }

}

