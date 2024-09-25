package com.example.iorms

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.iorms.ui.theme.poppinsFontFamily
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import okhttp3.ResponseBody


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(navController: NavController, empDataString: String, apiResponseBody: ApiResponse) {

    var search by remember { mutableStateOf(TextFieldValue()) }
    var resourceList by remember { mutableStateOf<List<ResourceDetails1>>(emptyList()) }
    var searchPerformed by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

    Log.e("EMP String ",empDataString)

    Column(modifier = Modifier.fillMaxSize()) {
        // Header Row
        Row(
            modifier= Modifier
                .fillMaxWidth()
                .height(81.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.your_app_logo),
                "",
                modifier= Modifier
                    .padding(top = 20.dp, start = 20.dp)
                    .width(100.dp)
                    .height(50.dp)

            )
            Text(text = "INFORMS",
                fontSize = 20.sp,
                color = Color.Black,
                fontFamily = poppinsFontFamily,
                modifier= Modifier
                    .padding(top = 30.dp, start = 8.dp)
                    .width(90.dp)
                    .height(23.dp))

            Spacer(modifier = Modifier.weight(1f)) // Spacer to push the following column to the end
            Column(
                modifier = Modifier.padding(end = 16.dp, top = 25.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
//                    Text(
//                        text = firstName,
//                        fontSize = 12.sp,
//                        fontFamily = poppinsFontFamily,
//                        color = Color.Black,
//                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable {
                            navController.navigate("LoginScreen")
                        }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.humbleicons_logout),
                            contentDescription = "Logout",
                            modifier = Modifier
                                .height(27.dp)
                                .width(32.dp)
                        )
                        Text(
                            text = "Logout",
                            fontSize = 8.sp,
                            fontFamily = poppinsFontFamily,
                            color = Color.Black,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }

        }
        Row (
            modifier = Modifier
                .fillMaxWidth()

        ){Divider(
            color = Color(0xFFE3E3E3),
            modifier = Modifier
                .padding(start = 8.dp, top = 8.dp)
                .height(1.dp)
                .fillMaxWidth())

        }

        // Search TextField and Resource List
        Row(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.ion_chevron_back),
                contentDescription = "Back",
                modifier = Modifier
                    .padding(start = 10.dp, top = 25.dp)
                    .size(30.dp)
                    .clickable {

                        Log.e("EMP String ",empDataString)
                        if(empDataString.contains("PMC",true) || apiResponseBody.loginResponse.RoleName.equals("PMC",true)){
                            val keyValuePairs = empDataString.split(", ")

                            val keyValueMap = mutableMapOf<String, String>()

                            for (pair in keyValuePairs) {

                                val parts = pair.split(":")
                                if (parts.size == 2) {

                                    val key = parts[0].trim()
                                    val value = parts[1].trim()

                                    // Add key-value pair to the map
                                    keyValueMap[key] = value
                                }
                            }


                            val gson = Gson()
                            val apiResponseBodyJson = gson.toJson(apiResponseBody)
                            navController.navigate("PmcMainScreen/${keyValueMap.getValue("resourceId")}/${keyValueMap.getValue("firstName")}/${keyValueMap.getValue("role")}/${keyValueMap.getValue("selectedTab")}/${keyValueMap.getValue("selectedClient")}/$apiResponseBodyJson")
                        }
                        else{
                        navController.navigate("MainScr/$empDataString")
                        }
                    }
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp, top = 15.dp, end = 18.dp)
                    .border(
                        width = 0.2.dp,
                        color = Color.Gray,
                        shape = RoundedCornerShape(30.dp)
                    )
            ) {
                TextField(
                    value = search,
                    onValueChange = { search = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(horizontal = 8.dp),
                    placeholder = {
                        Text(
                            text = "Search Employee",
                            fontFamily = poppinsFontFamily,
                            color = Color(0xFFE3E3E3),
                            fontSize = 16.sp,
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        textColor = Color.Black,
                        focusedIndicatorColor = Color(0xffe3e3e3),
                        unfocusedIndicatorColor = Color(0xffe3e3e3)
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            keyboardController?.hide()
                        }
                    ),
                    trailingIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.octicon_search),
                            contentDescription = "Search",
                            modifier = Modifier
                                .size(20.dp)
                                .clickable {
                                    searchPerformed = true // Set searchPerformed to true
                                    if (search.text.isNotBlank()) {
                                        GlobalScope.launch(Dispatchers.IO) {
                                            try {
                                                val response = ApiClient.providesApiService().searchResource(search.text)
                                                val responseBody: ResponseBody? = response.body()
                                                Log.e("helllo", responseBody.toString())
                                                if (responseBody != null) {
                                                    val responseString: String = responseBody.string()
                                                    Log.e("helllo1", responseString)
                                                    val resourceListFromJson: List<ResourceDetails1> = Gson().fromJson(
                                                        responseString,
                                                        object : TypeToken<List<ResourceDetails1>>() {}.type
                                                    )
                                                    Log.e("helllo2", resourceListFromJson.toString())
                                                    val finalList = resourceListFromJson.sortedWith(compareByDescending<ResourceDetails1> {
                                                        it.FIRST_NAME.contains(search.text, ignoreCase = true)
                                                    }.thenByDescending {
                                                        it.LAST_NAME.contains(search.text, ignoreCase = true)
                                                    }.thenByDescending {
                                                        it.INTERNAL_LEAD_NAME.contains(search.text, ignoreCase = true)
                                                    })

                                                    // Assign the sorted list to resourceList or use it as needed
//                                                    val resourceList = finalList
                                                    resourceList = finalList
                                                } else {
                                                    resourceList = emptyList()
                                                    Log.e("", "Response body is null")
                                                }
                                            } catch (e: Exception) {
                                                Log.e("Error: ", "${e.message}")
                                            }
                                        }
                                    }
                                }
                        )
                    }
                )
            }
        }

        // Resource List or "No results found" message
        if (resourceList.isEmpty() && searchPerformed) {
            Text(
                text = "No results found",
                fontFamily = poppinsFontFamily,
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.padding(top = 16.dp, start = 16.dp)
            )
        } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
        ) {
            items(resourceList) { resource ->
                ResourceItem(resource = resource)
            }
        }
    }
    }
}

@Composable
fun ResourceItem(resource: ResourceDetails1) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(18.dp)
            .background(Color.White),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,)

        //backgroundColor = Color.White, // Set background color to white
        //elevation = 4.dp

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Name:",
                    fontFamily = poppinsFontFamily,
                    color = Color.Gray,
                    fontSize = 15.sp
                )
                Text(
                    text = "${resource.FIRST_NAME ?: ""} ${resource.LAST_NAME ?: ""}",
                    fontFamily = poppinsFontFamily,
                    fontSize = 15.sp
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Email:",
                    fontFamily = poppinsFontFamily,
                    color = Color.Gray,
                    fontSize = 15.sp
                )
                Text(
                    text = resource.EMAIL_ID ?: "",
                    fontFamily = poppinsFontFamily,
                    fontSize = 15.sp
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Resource ID:",
                    fontFamily = poppinsFontFamily,
                    color = Color.Gray,
                    fontSize = 15.sp
                )
                Text(
                    text = resource.RESOURCE_ID ?: "",
                    fontFamily = poppinsFontFamily,
                    fontSize = 15.sp
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Internal Lead:",
                    fontFamily = poppinsFontFamily,
                    color = Color.Gray,
                    fontSize = 15.sp
                )
                Text(
                    text = resource.INTERNAL_LEAD_NAME ?: "",
                    fontFamily = poppinsFontFamily,
                    fontSize = 15.sp
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Current Project:",
                    fontFamily = poppinsFontFamily,
                    color = Color.Gray,
                    fontSize = 15.sp
                )
                Text(
                    text = resource.PROJECT_NAME ?: "-",
                    fontFamily = poppinsFontFamily,
                    fontSize = 15.sp
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Current Client Lead:",
                    fontFamily = poppinsFontFamily,
                    color = Color.Gray,
                    fontSize = 15.sp
                )
                Text(
                    text = resource.CLIENT_LEAD_NAME ?: "-",
                    fontFamily = poppinsFontFamily,
                    fontSize = 15.sp
                )
            }
        }
    }
}