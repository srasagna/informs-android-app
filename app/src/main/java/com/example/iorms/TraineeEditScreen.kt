package com.example.iorms

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.iorms.ui.theme.poppinsFontFamily
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TraineeEditScreen(navController: NavController, firstName: String, resourceId: String, role: String, selectedTab: String,apiResponse: ApiResponse, selectedClient: String) {
    val scrollState = rememberScrollState()
    var firstName_1 by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var resourceId_1 by remember { mutableStateOf("") }
    var resourceEmail by remember { mutableStateOf("") }
    var resourceType by remember { mutableStateOf("") }
    var areaOfTechnology by remember { mutableStateOf("") }
    var infomericaOnboard by remember { mutableStateOf("") }
    var Degree by remember { mutableStateOf("") }
    var passedOut by remember { mutableStateOf("") }
    var college by remember { mutableStateOf("") }
    var experience by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var infomericaLastDate by remember { mutableStateOf("") }
    var apiResponseBody by remember { mutableStateOf<DetailedViewApiResponse?>(null) }
    var resourceDetails by remember { mutableStateOf<TraineeDetails?>(null) }
    var leadHistory by remember { mutableStateOf<List<LeadsProjectsHistory>?>(null) }
    val gson = Gson()
    val apiResponseBodyJson = gson.toJson(apiResponse)
    var selectedTechStackName by remember { mutableStateOf("") }
    var selectedTechStackId by remember { mutableStateOf(0) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }
    var shouldNavigate by remember { mutableStateOf(false) }
    var navigationRoute by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val userResponse = ApiClient.providesApiService().detailedViewOfEmpOrConsul(selectedTab, resourceId)
                Log.e("userResponse", userResponse.toString())

                if (userResponse.isSuccessful) {
                    apiResponseBody = userResponse.body()
                    Log.e("apiResponseBody", userResponse.toString())
                    Log.e("apiResponseBody", userResponse.body().toString())
                    if (apiResponseBody != null) {
                        resourceDetails = apiResponseBody!!.traineeDetails
                        leadHistory = apiResponseBody!!.leadsProjectsHistory

                        // Update state variables with the fetched data
                        resourceDetails?.let { details ->
                            firstName_1 = details.firstName.toString()
                            lastName = details.lastName.toString()
                            resourceId_1 = details.resourceId
                            resourceEmail = details.emailId.toString()
                            areaOfTechnology = details.areaOfTechnology.toString()
                            resourceType = details.resourceType.toString()
                            infomericaOnboard = details.infomericaOnboardedDate.toString()
                            Degree = details.degreeAndSpecialization.toString()
                            passedOut = details.passedOutYear.toString()
                            college = details.college.toString()
                            experience = details.experience.toString()
                            notes = details.notes.toString()
                            infomericaLastDate = details.infomericaLastWorkingDate.toString()
                        }
                    } else {
                        Log.e("ResponseError", "API response body is null")
                    }
                } else {
                    Log.e("ResponseError", "API response was not successful: ${userResponse.code()}")
                }
            } catch (e: Exception) {
                Log.e("APIError", "An error occurred during the API call: ${e.message}")
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.your_app_logo),
                contentDescription = "App Logo",
                modifier = Modifier
                    .padding(top = 0.dp, start = 0.dp)
                    .size(100.dp)
            )
            Text(
                text = "INFORMS",
                fontSize = 20.sp,
                fontFamily = poppinsFontFamily,
                color = Color.Black,
                modifier = Modifier
                    .padding(top = 10.dp, start = 8.dp)
            )
            Spacer(modifier = Modifier.weight(1f)) // Spacer to push the following column to the end
            Column(
                modifier = Modifier.padding(end = 0.dp, top = 8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = firstName,
                        fontSize = 16.sp,
                        fontFamily = poppinsFontFamily,
                        color = Color.Black,
                    )
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

        Row(
            modifier = Modifier
                .fillMaxWidth()

        ) {
            Divider(
                color = Color(0xFFE3E3E3),
                modifier = Modifier
                    .padding(start = 0.dp, top = 8.dp, end = 0.dp)
                    .height(1.dp)
                    .fillMaxWidth()
            )

        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically // Ensure vertical alignment
        ) {
            IconButton(
                onClick = { navController.navigate("PmcMainScreen/resourceIdValue/$firstName/roleValue/$selectedTab/$selectedClient/$apiResponseBodyJson") },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "Close",
                    tint = Color.LightGray
                )
            }
            Spacer(modifier = Modifier.width(16.dp)) // Spacer to add some space between the icon and the text

            Box(
                modifier = Modifier
                    .width(240.dp), // Use weight to take available space
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Resource Details",
                    fontSize = 20.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Bold,
                )
            }
            Spacer(modifier = Modifier.width(20.dp)) // Spacer to add some space between the text and the button

            Text(
                text = "Done",
                fontSize = 15.sp,
                color = Color(0xFF4CAF50),
                modifier = Modifier
                    .clickable {
                        val resourceDetailsRequest = ResourceDetailsRequest(
                            firstName = firstName_1,
                            lastName = lastName,
                            resourceId = resourceId_1,
                            infomericaOnboardedDate = infomericaOnboard,
                            infomericaLastWorkingDate = infomericaLastDate,
                            resourceType = resourceType,
                            emailId = resourceEmail,
                            techStackId = selectedTechStackId,
                            notes = notes,
                            degreeAndSpecialization =Degree ,
                            passedOutYear = passedOut,
                            college = college,
                            experience = experience,
                            modifiedBy = apiResponse.loginResponse.email_id
                        )

                        sendResourceDetails("selectedTabValue", resourceDetailsRequest, onSuccess = {
                            dialogMessage = "Update successful"
                            showSuccessDialog = true
                        }) { errorMessage ->
                            dialogMessage = errorMessage
                            showErrorDialog = true
                        }
//                        navController.navigate("PmcMainScreen/resourceIdValue/$firstName/roleValue/$selectedTab/$apiResponseBodyJson")
                    }
            )
        }
        if (showSuccessDialog) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Transparent.copy(alpha = 0.1f),
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    PopupDialog(
                        navController = navController,
                        firstName = firstName,
                        resourceId = resourceId,
                        role = role,
                        selectedTab = selectedTab,
                        apiResponse = apiResponse,
                        onDismiss = { showSuccessDialog = false }
                    )
                }
            }
        }
        // Show Error Dialog
        if (showErrorDialog) {
            AlertDialog(
                onDismissRequest = { showErrorDialog = false },
                confirmButton = {
                    Button(onClick = { showErrorDialog = false

                    }) {
                        Text("OK")
                    }
                },
                title = { Text("Error") },
                text = { Text(dialogMessage) }

            )
        }

        OutlinedTextField(
            value = firstName_1,
            onValueChange = { firstName_1 = it },
            label = { Text("First Name") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Last Name") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = resourceId_1,
            onValueChange = { resourceId_1 = it },
            label = { Text("Resource ID") },
            modifier = Modifier.fillMaxWidth()
        )
        ResourceTraineeTypeDropdown(resourceType) { selectedType ->
            resourceType = selectedType
        }
        OutlinedTextField(
            value = resourceEmail,
            onValueChange = { resourceEmail = it },
            label = { Text("Resource Email ID") },
            modifier = Modifier.fillMaxWidth()
        )

        apiResponseBody?.traineeDetails?.areaOfTechnology?.let { initialSelectedOption ->
            // Find the initial item based on the tech stack name
            val initialItem = apiResponse.listsForDropDown.P_TECH_STACK_REF
                .find { it.techStackName == initialSelectedOption }
            selectedTechStackName = initialItem?.techStackName.toString()
            selectedTechStackId = initialItem?.techStackId!!

            GenericDropdown(
                itemList = apiResponse.listsForDropDown.P_TECH_STACK_REF,
                initialSelectedOption = initialSelectedOption,
                getItemDisplay = { it.techStackName },
                getItemValue = { it.techStackId }
            ) { selectedName, selectedId ->
                selectedTechStackName = selectedName
                selectedTechStackId = selectedId as Int
            }
        }


        OutlinedTextField(
            value = infomericaOnboard,
            onValueChange = { infomericaOnboard = it },
            label = { Text("Infomerica Onboard Date") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = Degree,
            onValueChange = { Degree = it },
            label = { Text("Degree and Specification") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = passedOut,
            onValueChange = { passedOut = it },
            label = { Text("Passed out year") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = college,
            onValueChange = { college = it },
            label = { Text("College/University") },
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
        )
        OutlinedTextField(
            value = experience,
            onValueChange = { experience = it },
            label = { Text("Experience, If any") },
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
        )
        OutlinedTextField(
            value = notes,
            onValueChange = { notes = it },
            label = { Text("Notes") },
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
        )
        OutlinedTextField(
            value = infomericaLastDate,
            onValueChange = { infomericaLastDate = it },
            label = { Text("Infomerica Last Working Date") },
            modifier = Modifier.fillMaxWidth()
        )


    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> GenericDropdown(
    itemList: List<T>,
    initialSelectedOption: String,
    getItemDisplay: (T) -> String,
    getItemValue: (T) -> Any,
    onOptionSelected: (String, Any) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(initialSelectedOption) }

    Column {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = { },
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Area of Technology") },
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(Icons.Filled.ArrowDropDown, contentDescription = "Toggle Dropdown")
                }
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(250.dp) // Match the width of the text field
                .background(Color.White) // Set the background color to white
        ) {
            itemList.forEach { item ->
                val displayValue = getItemDisplay(item)
                val itemValue = getItemValue(item)
                DropdownMenuItem(
                    text = { Text(displayValue) },
                    onClick = {
                        onOptionSelected(displayValue, itemValue)
                        selectedOption = displayValue
                        expanded = false
                    }
                )
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResourceTraineeTypeDropdown(selectedOption: String, onOptionSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = { },
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Resource Type") },
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(Icons.Filled.ArrowDropDown, contentDescription = "Toggle Dropdown")
                }
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(250.dp) // Match the width of the text field
                .background(Color.White) // Set the background color to white
        ) {
            DropdownMenuItem(
                text = { Text("Employee") },
                onClick = {
                    onOptionSelected("Employee")
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Consultant") },
                onClick = {
                    onOptionSelected("Consultant")
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Trainee") },
                onClick = {
                    onOptionSelected("Trainee")
                    expanded = false
                }
            )
        }
    }
}


fun sendResourceDetails(
    selectedTab: String,
    resourceDetailsRequest: ResourceDetailsRequest,
    onSuccess: () -> Unit,
    onFailure: (String) -> Unit
) {

    val call = ApiClient.providesApiService().updateResourceDetails("Trainees", resourceDetailsRequest)
    Log.e("h",call.toString())
    call.enqueue(object : Callback<String> {
        override fun onResponse(call: Call<String>, response: Response<String>) {
            if (response.isSuccessful) {
                onSuccess()
                Log.e("API call successful. Response message:", response.body().toString())
            } else {
                Log.e("API call failed with response code: ",response.code().toString())
                onFailure(response.code().toString())
            }
        }

        override fun onFailure(call: Call<String>, t: Throwable) {
            onFailure(t.message.toString())
            Log.e("Failed",t.message.toString())
        }
    })



}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PopupDialog(
    navController: NavController,
    firstName: String,
    resourceId: String,
    role: String,
    selectedTab: String,
    apiResponse: ApiResponse,
    onDismiss: () -> Unit
)  {
    val gson = Gson()
    val apiResponseBodyJson = gson.toJson(apiResponse)
    Dialog(
        onDismissRequest = { onDismiss() }
    ) {
        Surface(
            modifier = Modifier
                .width(300.dp)
                .height(250.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(8.dp),
            //elevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.White)
            ) {
                Image(painter = painterResource(id = R.drawable.tick), contentDescription = "tick mark",
                    modifier=Modifier
                        .height(40.dp)
                        .fillMaxWidth())
                Text(
                    text = "Updated\nSuccessfully",
                    fontFamily = poppinsFontFamily,
                    fontSize = 16.sp, textAlign = TextAlign.Center,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 50.dp, top = 15.dp)
                )
                Button(
                    onClick = { onDismiss()
//                        "PmcMainScreen/resourceIdValue/$firstName/roleValue/$selectedTab/$selectedClient/$apiResponseBodyJson")
                        navController.navigate("PmcMainScreen/resourceIdValue/$firstName/roleValue/$selectedTab/INFOMERICA/$apiResponseBodyJson") },
                    modifier = Modifier
                        .width(160.dp)
                        .height(60.dp)
                        .padding(top = 20.dp, start = 70.dp),
                    shape = RoundedCornerShape(7.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD2F6DC))

                ) {
                    Text(text = "OK", fontSize = 15.sp,
                        fontFamily = poppinsFontFamily,
                        modifier = Modifier
                        , color = Color.Black)
                }
            }
        }
    }
}



//@Preview(showBackground = true)
//@Composable
//fun Preview1() {
//    TraineeEditScreen(navController = rememberNavController(), firstName = "", resourceId = "", role = "", selectedTab = "")
//}
