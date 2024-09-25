package com.example.iorms

import android.app.DatePickerDialog
import android.util.Log
import android.widget.DatePicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.iorms.ui.theme.poppinsFontFamily
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TraineeAddScreen(navController: NavController, firstName: String, selectedTab: String , apiResponseBody:ApiResponse, selectedClient: String) {
    val scrollState = rememberScrollState()
    var firstName1 by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var resourceId1 by remember { mutableStateOf("") }
    var resourceEmail by remember { mutableStateOf("") }
    var areaOfTechnology by remember { mutableStateOf("") }
    var degreeandSpecialization by remember { mutableStateOf("") }
    var passedoutyear by remember { mutableStateOf("") }
    var collegeOrUniversity by remember { mutableStateOf("") }
    var experience by remember { mutableStateOf("") }
    var infomericaOnboardDate by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var infomericaLastWorkingDate by remember { mutableStateOf("") }
    var showErrorInfomericaLastWorkingDate by remember { mutableStateOf(false) }

    var showErrorFirstName by remember { mutableStateOf(false) }
    var showErrorLastName by remember { mutableStateOf(false) }
    var showErrorResourceId by remember { mutableStateOf(false) }
    var showErrorResourceEmail by remember { mutableStateOf(false) }
    var showErrorAreaOfTechnology by remember { mutableStateOf(false) }
    var showErrorDegreeandSpecialization by remember { mutableStateOf(false) }
    var showErrorPassedoutyear by remember { mutableStateOf(false) }
    var showErrorCollegeOrUniversity by remember { mutableStateOf(false) }
    var showErrorInfomericaOnboardDate by remember { mutableStateOf(false) }

    val gson = Gson()
    val apiResponseBodyJson = gson.toJson(apiResponseBody)
    var selectedTechStackName by remember { mutableStateOf("") }
    var selectedTechStackId by remember { mutableStateOf(0) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }
    var shouldNavigate by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ){
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
            //   .padding(16.dp), // Add padding if needed
            verticalAlignment = Alignment.CenterVertically // Ensure vertical alignment
        ) {
            IconButton(
//                fun PmcMainScreen(navController: NavController, resourceId:String, firstName:String,role:String,selectedTab: String, apiResponseBody: ApiResponse, selectedClient: String) {
//        composable(route = "PmcMainScreen/{resourceId}/{firstName}/{role}/{selectedTab}/{selectedClient}/{data}") { backStackEntry ->
//                                        navController.navigate("PmcMainScreen/resourceIdValue/$firstName/roleValue/$selectedTab/INFOMERICA/$apiResponseBodyJson") },
                    onClick = { navController.navigate("PmcMainScreen/resourceIdValue/$firstName/roleValue/$selectedTab/INFOMERICA/$apiResponseBodyJson") },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "Close",
                    tint = Color.LightGray
                )
            }
            Spacer(modifier = Modifier.width(16.dp))

            Box(
                modifier = Modifier
                    .width(240.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Resource Details",
                    fontSize = 20.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Bold,
                )
            }
            Spacer(modifier = Modifier.width(30.dp))

            Text(
                text = "Done",
                fontSize = 15.sp,
                color = Color(0xFF4CAF50),
                modifier = Modifier
                    // .padding(top = 6.dp)
                    .clickable {
                        Log.d("TraineeAddScreen", "Done button clicked")
                        showErrorFirstName = firstName1.isBlank()
                        showErrorLastName = lastName.isBlank()
                        showErrorResourceId = resourceId1.isBlank()
                        showErrorResourceEmail = resourceEmail.isBlank()
                        showErrorAreaOfTechnology = selectedTechStackName.isBlank()
                        showErrorDegreeandSpecialization = degreeandSpecialization.isBlank()
                        showErrorPassedoutyear = passedoutyear.isBlank()
                        showErrorCollegeOrUniversity = collegeOrUniversity.isBlank()
                        showErrorInfomericaOnboardDate = infomericaOnboardDate.isBlank()

                        val isError = showErrorFirstName || showErrorLastName || showErrorResourceId || showErrorResourceEmail ||
                                showErrorAreaOfTechnology || showErrorDegreeandSpecialization || showErrorPassedoutyear ||
                                showErrorCollegeOrUniversity || showErrorInfomericaOnboardDate

                        if (!isError) {
                            val resourceDetailsRequest = ResourceAdd(
                                firstName = firstName1,
                                lastName = lastName,
                                resourceId = resourceId1,
                                infomericaOnboardedDate = infomericaOnboardDate,
                                resourceType = "Trainee",
                                emailId = resourceEmail,
                                techStackId = selectedTechStackId,
                                notes = notes,
                                degreeSpecification = degreeandSpecialization,
                                passedOutYear = passedoutyear,
                                collegeName = collegeOrUniversity,
                                experience = experience,
                                createdBy = apiResponseBody.loginResponse.email_id
                            )

                            sendResourceDetailsForTraineeAdd(selectedTab, resourceDetailsRequest, onSuccess = {
                                Log.d("TraineeAddScreen", "API call successful")
                                dialogMessage = "Update successful"
                                showSuccessDialog = true
                            }, onFailure = { errorMessage ->
                                Log.e("TraineeAddScreen", "API call failed: $errorMessage")
                                dialogMessage = errorMessage
                                showErrorDialog = true
                            })
                        }
                        else {
                            Log.d("TraineeAddScreen", "Form validation errors")
                        }
                    }
            )
        }

        if (showSuccessDialog) {
            AlertDialog(
                onDismissRequest = { showSuccessDialog = false },
                confirmButton = {
                    Button(onClick = {
                        showSuccessDialog = false
                        navController.navigate("PmcMainScreen/resourceIdValue/$firstName/roleValue/$selectedTab/INFOMERICA/$apiResponseBodyJson")
                        shouldNavigate = true
                    }) {
                        Text("OK")
                    }
                },
                title = { Text("Success") },
                text = { Text(dialogMessage) }
            )
        }

        // Show Error Dialog
        if (showErrorDialog) {
            AlertDialog(
                onDismissRequest = { showErrorDialog = false },
                confirmButton = {
                    Button(onClick = { showErrorDialog = false }) {
                        Text("OK")
                    }
                },
                title = { Text("Error") },
                text = { Text(dialogMessage) }

            )
        }

        OutlinedTextField(
            value = firstName1,
            onValueChange = {
                firstName1 = it
                showErrorFirstName = firstName1.isBlank()
            },
            label = { Text("First Name") },
            modifier = Modifier.fillMaxWidth(),
            isError = showErrorFirstName
        )
        if (showErrorFirstName) {
            Text(
                text = "Please fill this field.",
                color = Color.Red,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        OutlinedTextField(
            value = lastName,
            onValueChange = {
                lastName = it
                showErrorLastName = lastName.isBlank()
            },
            label = { Text("Last Name") },
            modifier = Modifier.fillMaxWidth(),
            isError = showErrorLastName
        )
        if (showErrorLastName) {
            Text(
                text = "Please fill this field.",
                color = Color.Red,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        OutlinedTextField(
            value = resourceId1,
            onValueChange = {
                resourceId1 = it
                showErrorResourceId = resourceId1.isBlank()
            },
            label = { Text("Resource ID") },
            modifier = Modifier.fillMaxWidth(),
            isError = showErrorResourceId
        )
        if (showErrorResourceId) {
            Text(
                text = "Please fill this field.",
                color = Color.Red,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        OutlinedTextField(
            value = resourceEmail,
            onValueChange = {
                resourceEmail = it
                showErrorResourceEmail = resourceEmail.isBlank()
            },
            label = { Text("Resource Email ID") },
            modifier = Modifier.fillMaxWidth(),
            isError = showErrorResourceEmail
        )
        if (showErrorResourceEmail) {
            Text(
                text = "Please fill this field.",
                color = Color.Red,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        GenericDropdownForTraineeAdd(
            itemList = apiResponseBody.listsForDropDown.P_TECH_STACK_REF,
            getItemDisplay = { it.techStackName },
            getItemValue = { it.techStackId },
            onOptionSelected = { selectedName, selectedId ->
                selectedTechStackName = selectedName
                selectedTechStackId = selectedId as Int
                showErrorAreaOfTechnology = selectedTechStackName.isBlank()
            },
            showAreaOfTechnologyError = showErrorAreaOfTechnology
        )
        if (showErrorAreaOfTechnology) {
            Text(
                text = "Please fill this field.",
                color = Color.Red,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
        DatePickerTextField(
            label = "Infomerica OnBoarded Date",
            value = infomericaOnboardDate,
            onValueChange = {
                infomericaOnboardDate = it
                showErrorInfomericaOnboardDate = infomericaOnboardDate.isBlank()
            },
            isError = showErrorInfomericaOnboardDate,
            modifier = Modifier.fillMaxWidth()
        )

        if (showErrorInfomericaOnboardDate) {
            Text(
                text = "",
                color = Color.Red,
                modifier = Modifier.padding(start = 16.dp)
            )
        }


        OutlinedTextField(
            value = degreeandSpecialization,
            onValueChange = {
                degreeandSpecialization = it
                showErrorDegreeandSpecialization = degreeandSpecialization.isBlank()
            },
            label = { Text("Degree and Specialization") },
            modifier = Modifier.fillMaxWidth(),
            isError = showErrorDegreeandSpecialization
        )
        if (showErrorDegreeandSpecialization) {
            Text(
                text = "Please fill this field.",
                color = Color.Red,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        OutlinedTextField(
            value = passedoutyear,
            onValueChange = {
                passedoutyear = it
                showErrorPassedoutyear = passedoutyear.isBlank()
            },
            label = { Text("Passed out year") },
            modifier = Modifier.fillMaxWidth(),
            isError = showErrorPassedoutyear
        )
        if (showErrorPassedoutyear) {
            Text(
                text = "Please fill this field.",
                color = Color.Red,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        OutlinedTextField(
            value = collegeOrUniversity,
            onValueChange = {
                collegeOrUniversity = it
                showErrorCollegeOrUniversity = collegeOrUniversity.isBlank()
            },
            label = { Text("College/University") },
            modifier = Modifier.fillMaxWidth(),
            isError = showErrorCollegeOrUniversity
        )
        if (showErrorCollegeOrUniversity) {
            Text(
                text = "Please fill this field.",
                color = Color.Red,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        OutlinedTextField(
            value = experience,
            onValueChange = { experience = it },
            label = { Text("Experience if any") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = notes,
            onValueChange = { notes = it },
            label = { Text("Notes 1") },
            modifier = Modifier.fillMaxWidth()
        )
        DatePickerTextField(
            label = "Infomerica Last Working Date",
            value = infomericaLastWorkingDate,
            onValueChange = {
                infomericaLastWorkingDate = it
                showErrorInfomericaLastWorkingDate = false
            },
            isError = false,
            modifier = Modifier.fillMaxWidth()
        )

    }


}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    // Date format as required
    val dateFormatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            // Set the calendar to the selected date
            calendar.set(year, month, dayOfMonth)
            // Format the date as per the required format
            onValueChange(dateFormatter.format(calendar.time))
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
    )

    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            modifier = Modifier.fillMaxWidth().clickable { datePickerDialog.show() },
            isError = isError,
            trailingIcon = {
                IconButton(onClick = { datePickerDialog.show() }) {
                    Icon(imageVector = Icons.Default.CalendarToday, contentDescription = "Select date")
                }
            }
        )
        if (isError) {
            Text(
                text = "Please fill this field.",
                color = Color.Red,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> GenericDropdownForTraineeAdd(
    itemList: List<T>,
    getItemDisplay: (T) -> String,
    getItemValue: (T) -> Any,
    onOptionSelected: (String, Any) -> Unit,
    showAreaOfTechnologyError: Boolean
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("") }

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
fun sendResourceDetailsForTraineeAdd(
    selectedTab: String,
    resourceDetailsRequest: ResourceAdd,
    onSuccess: () -> Unit,
    onFailure: (String) -> Unit
) {
    Log.e("resourceDetailsRequest",resourceDetailsRequest.toString())
    val call = ApiClient.providesApiService().insertDetails(resourceDetailsRequest)
    Log.e("API Call", call.toString())
    call.enqueue(object : Callback<String> {
        override fun onResponse(call: Call<String>, response: Response<String>) {
            if (response.isSuccessful) {
                Log.e("API Success", "Response: ${response.body()}")
                onSuccess()
            } else {
                Log.e("API Failure", "Response Code: ${response.code()}")
                Log.e("API Failure", "Response Code: ${response.raw()}")
                Log.e("API Failure", "Response Code: ${response.body()}")
                Log.e("API Failure", "Response Code: ${response.message()}")
                Log.e("API Failure", "Response Code: ${response.headers()}")
                Log.e("API Failure", "Response Code: ${response.errorBody()}")
                onFailure("Error: ${response.code()}")
            }
        }

        override fun onFailure(call: Call<String>, t: Throwable) {
            Log.e("API Failure", "Error: ${t.message}")
            onFailure("Insertion of a resource failed: ${t.message}")
        }
    })
}


