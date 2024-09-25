package com.example.iorms

import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeeAddScreen(navController: NavController, firstName: String, selectedTab: String, apiResponseBody: ApiResponse, selectedClient: String) {
    val scrollState = rememberScrollState()
    var firstName1 by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var resourceId1 by remember { mutableStateOf("") }
    var resourceEmail by remember { mutableStateOf("") }
    var areaOfTechnology by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("") }
    var infomericaLeadName by remember { mutableStateOf("") }
    var resourceCurrentPosition by remember { mutableStateOf("") }
    var resourceCurrentLocation by remember { mutableStateOf("") }
    var infomericaOnboardDate by remember { mutableStateOf("") }
    var visaType by remember { mutableStateOf("") }
    var visaValidityDate by remember { mutableStateOf("") }
    var clientName by remember { mutableStateOf("") }
    var nrgSapId by remember { mutableStateOf("") }
    var nrgWorkerId by remember { mutableStateOf("") }
    var clientLeadName by remember { mutableStateOf("") }
    var currentProject by remember { mutableStateOf("") }
    var clientPosition by remember { mutableStateOf("") }
    var clientOnboardDate by remember { mutableStateOf("") }
    var clientBillingStartDate by remember { mutableStateOf("") }
    var notes1 by remember { mutableStateOf("") }
    var notes2 by remember { mutableStateOf("") }
    var isBillingChecked by remember { mutableStateOf(true) }
    var isVisaDetailsChecked by remember { mutableStateOf(false) }
    val gson = Gson()
    val apiResponseBodyJson = gson.toJson(apiResponseBody)

    var showErrorFirstName by remember { mutableStateOf(false) }
    var showErrorLastName by remember { mutableStateOf(false) }
    var showErrorResourceId by remember { mutableStateOf(false) }
    var showErrorResourceEmail by remember { mutableStateOf(false) }
    var showErrorAreaOfTechnology by remember { mutableStateOf(false) }
    var showErrorRole by remember { mutableStateOf(false) }
    var showErrorInfomericaLeadName by remember { mutableStateOf(false) }
    var showErrorResourceCurrent by remember { mutableStateOf(false) }
    var showErrorResourceCurrentLocation by remember { mutableStateOf(false) }
    var showErrorResourceClientName by remember { mutableStateOf(false) }
    var showErrorResourceSapId by remember { mutableStateOf(false) }
    var showErrorResourceWorkerId by remember { mutableStateOf(false) }
    var showErrorClientLeadName by remember { mutableStateOf(false) }
    var showErrorCurrentProject by remember { mutableStateOf(false) }
    var showErrorClientPosition by remember { mutableStateOf(false) }

    var showSuccessDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }
    var shouldNavigate by remember { mutableStateOf(false) }



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
            Spacer(modifier = Modifier.width(30.dp)) // Spacer to add some space between the text and the button

            Text(
                text = "Done",
                fontSize = 15.sp,
                color = Color(0xFF4CAF50),
                modifier = Modifier
                    .clickable {
                        showErrorFirstName = firstName1.isBlank()
                        showErrorLastName = lastName.isBlank()
                        showErrorResourceId = resourceId1.isBlank()
                        showErrorResourceEmail = resourceEmail.isBlank()
                        showErrorAreaOfTechnology = areaOfTechnology.isBlank()
                        showErrorRole = role.isBlank()
                        showErrorInfomericaLeadName = infomericaLeadName.isBlank()
                        showErrorResourceCurrent = resourceCurrentPosition.isBlank()
                        showErrorResourceCurrentLocation = resourceCurrentLocation.isBlank()
                        showErrorResourceClientName = clientName.isBlank()
                        showErrorResourceSapId = nrgSapId.isBlank()
                        showErrorResourceWorkerId  = nrgWorkerId.isBlank()
                        showErrorClientLeadName = clientLeadName.isBlank()
                        showErrorCurrentProject =currentProject.isBlank()
                        showErrorClientPosition = clientPosition.isBlank()

                        val isError = showErrorFirstName || showErrorLastName || showErrorResourceId || showErrorResourceEmail ||
                                showErrorAreaOfTechnology || showErrorRole || showErrorInfomericaLeadName ||
                                showErrorResourceCurrent || showErrorResourceCurrentLocation || showErrorResourceClientName ||
                                showErrorResourceSapId || showErrorResourceWorkerId || showErrorClientLeadName || showErrorCurrentProject ||
                                showErrorClientPosition

                        if (!isError) {
                            val resourceDetailsRequest = ResourceAdd(
                                firstName = firstName1,
                                lastName = lastName,
                                resourceId = resourceId1,
                                infomericaOnboardedDate = infomericaOnboardDate,
                                resourceType = "Employee",
                                emailId = resourceEmail,

                                createdBy = apiResponseBody.loginResponse.email_id
                            )

                            sendResourceDetailsForEmployeeAdd(selectedTab, resourceDetailsRequest, onSuccess = {
                                dialogMessage = "Update successful"
                                showSuccessDialog = true
                            }, onFailure = { errorMessage ->
                                dialogMessage = errorMessage
                                showErrorDialog = true
                            })
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
                        navController.navigate("PmcMainScreen/resourceIdValue/$firstName/roleValue/$selectedTab/$apiResponseBodyJson")
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
                showErrorFirstName = firstName1.isBlank() },
            label = { Text("First Name") },
            modifier = Modifier.fillMaxWidth(),
            isError = showErrorFirstName
        )
        if (showErrorFirstName) {
            Text(text = "Please fill this field.",
                color = Color.Red,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        OutlinedTextField(
            value = lastName,
            onValueChange = {
                lastName = it
                showErrorLastName = lastName.isBlank() },
            label = { Text("Last Name") },
            modifier = Modifier.fillMaxWidth(),
            isError = showErrorLastName
        )
        if (showErrorLastName) {
            Text(text = "Please fill this field.",
                color = Color.Red,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        OutlinedTextField(
            value = resourceId1,
            onValueChange = {
                resourceId1 = it
                showErrorResourceId = resourceId1.isBlank() },
            label = { Text("Resource ID") },
            modifier = Modifier.fillMaxWidth(),
            isError = showErrorResourceId
        )
        if (showErrorResourceId) {
            Text(text = "Please fill this field.",
                color = Color.Red,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            ResourceDropdown()

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.width(16.dp))
                Text("Billing", modifier = Modifier
                    .padding(end = 26.dp))
                Spacer(modifier = Modifier.height(0.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Switch(checked = isBillingChecked, onCheckedChange = { isBillingChecked = it })
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(if (isBillingChecked) "Yes" else "No")
                }
            }
        }

        OutlinedTextField(
            value = resourceEmail,
            onValueChange = {
                resourceEmail = it
                showErrorResourceEmail = resourceEmail.isBlank() },
            label = { Text("Resource Email ID") },
            modifier = Modifier.fillMaxWidth(),
            isError = showErrorResourceEmail
        )
        if (showErrorResourceEmail) {
            Text(text = "Please fill this field.",
                color = Color.Red,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        OutlinedTextField(
            value = areaOfTechnology,
            onValueChange = {
                areaOfTechnology = it
                showErrorAreaOfTechnology = areaOfTechnology.isBlank() },
            label = { Text("Area of Technology") },
            modifier = Modifier.fillMaxWidth(),
            isError = showErrorAreaOfTechnology
        )
        if (showErrorAreaOfTechnology) {
            Text(text = "Please fill this field.",
                color = Color.Red,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        OutlinedTextField(
            value = role,
            onValueChange = {
                role = it
                showErrorRole = role.isBlank() },
            label = { Text("Role") },
            modifier = Modifier.fillMaxWidth(),
            isError = showErrorRole
        )
        if (showErrorRole) {
            Text(text = "Please fill this field.",
                color = Color.Red,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        OutlinedTextField(
            value = infomericaLeadName,
            onValueChange = {
                infomericaLeadName = it
                showErrorInfomericaLeadName = infomericaLeadName.isBlank() },
            label = { Text("Infomerica Lead Name") },
            modifier = Modifier.fillMaxWidth(),
            isError = showErrorInfomericaLeadName
        )
        if (showErrorInfomericaLeadName) {
            Text(text = "Please fill this field.",
                color = Color.Red,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        OutlinedTextField(
            value = resourceCurrentPosition,
            onValueChange = {
                resourceCurrentPosition = it
                showErrorResourceCurrent = resourceCurrentPosition.isBlank() },
            label = { Text("Resource Current Position") },
            modifier = Modifier.fillMaxWidth(),
            isError = showErrorResourceCurrent
        )
        if (showErrorResourceCurrent) {
            Text(text = "Please fill this field.",
                color = Color.Red,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        OutlinedTextField(
            value = resourceCurrentLocation,
            onValueChange = {
                resourceCurrentLocation = it
                showErrorResourceCurrentLocation = resourceCurrentLocation.isBlank() },
            label = { Text("Resource Current Location") },
            modifier = Modifier.fillMaxWidth(),
            isError =  showErrorResourceCurrentLocation
        )
        if (showErrorResourceCurrentLocation) {
            Text(text = "Please fill this field.",
                color = Color.Red,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        DatePickerOutlinedTextField(label = "Infomerica Onboard Date")

        // Visa Details Section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Visa Details",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Switch(
                checked = isVisaDetailsChecked,
                onCheckedChange = { isVisaDetailsChecked = it }
            )
        }

        if (isVisaDetailsChecked) {
            OutlinedTextField(
                value = visaType,
                onValueChange = { visaType = it },
                label = { Text("Visa Type") },
                modifier = Modifier.fillMaxWidth()
            )

            DatePickerOutlinedTextField(label = "Visa Validity Date")
        }

        // Client Details Section
        Text(
            text = "Client Details",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
        )

        OutlinedTextField(
            value = clientName,
            onValueChange = {
                clientName = it
                showErrorResourceClientName = clientName.isBlank() },
            label = { Text("Client Name") },
            modifier = Modifier.fillMaxWidth(),
            isError = showErrorResourceClientName
        )
        if (showErrorResourceClientName) {
            Text(text = "Please fill this field.",
                color = Color.Red,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        OutlinedTextField(
            value = nrgSapId,
            onValueChange = {
                nrgSapId = it
                showErrorResourceSapId = nrgSapId.isBlank() },
            label = { Text("NRG SAP ID") },
            modifier = Modifier.fillMaxWidth(),
            isError = showErrorResourceSapId
        )
        if (showErrorResourceSapId) {
            Text(text = "Please fill this field.",
                color = Color.Red,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        OutlinedTextField(
            value = nrgWorkerId,
            onValueChange = {
                nrgWorkerId = it
                showErrorResourceWorkerId = nrgWorkerId.isBlank() },
            label = { Text("NRG Worker ID") },
            modifier = Modifier.fillMaxWidth(),
            isError = showErrorResourceWorkerId
        )
        if (showErrorResourceWorkerId) {
            Text(text = "Please fill this field.",
                color = Color.Red,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        OutlinedTextField(
            value = clientLeadName,
            onValueChange = {
                clientLeadName = it
                showErrorClientLeadName = clientLeadName.isBlank() },
            label = { Text("Client Lead Name") },
            modifier = Modifier.fillMaxWidth(),
            isError = showErrorClientLeadName
        )
        if (showErrorClientLeadName) {
            Text(text = "Please fill this field.",
                color = Color.Red,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        OutlinedTextField(
            value = currentProject,
            onValueChange = {
                currentProject = it
                showErrorCurrentProject = currentProject.isBlank() },
            label = { Text("Current Project") },
            modifier = Modifier.fillMaxWidth(),
            isError = showErrorCurrentProject
        )
        if (showErrorCurrentProject) {
            Text(text = "Please fill this field.",
                color = Color.Red,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        OutlinedTextField(
            value = clientPosition,
            onValueChange = {
                clientPosition = it
                showErrorClientPosition = clientPosition.isBlank() },
            label = { Text("Client Position") },
            modifier = Modifier.fillMaxWidth(),
            isError = showErrorClientPosition
        )
        if (showErrorClientPosition) {
            Text(text = "Please fill this field.",
                color = Color.Red,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        DatePickerOutlinedTextField(label = "Client Onboard Date")
        DatePickerOutlinedTextField(label = "Client Billing Start Date")

        OutlinedTextField(
            value = notes1,
            onValueChange = { notes1 = it },
            label = { Text("Notes 1") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = notes2,
            onValueChange = { notes2 = it },
            label = { Text("Notes 2") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResourceDropdown() {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Employee") }

    Column {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = { },
            readOnly = true,
            modifier = Modifier.width(250.dp),
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
            modifier = Modifier.fillMaxWidth()
        ) {
            DropdownMenuItem(
                text = { Text("Employee") },
                onClick = {
                    selectedOption = "Employee"
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Consultant") },
                onClick = {
                    selectedOption = "Consultant"
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Trainee") },
                onClick = {
                    selectedOption = "Trainee"
                    expanded = false
                }
            )
        }
    }
}

fun sendResourceDetailsForEmployeeAdd(
    selectedTab: String,
    resourceDetailsRequest: ResourceAdd,
    onSuccess: () -> Unit,
    onFailure: (String) -> Unit
) {

    val call = ApiClient.providesApiService().insertDetails( resourceDetailsRequest)
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
            onFailure("Insertion of a resource failed")
            Log.e("Failed",t.message.toString())
        }
    })



}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeeAddScreenPreview() {
    val navController = rememberNavController() // Mock NavController for preview
    EmployeeAddScreen(
        navController = navController,
        firstName = "John",
        selectedTab = "Overview",
        apiResponseBody = ApiResponse(), // Mock ApiResponse for preview
        selectedClient = "Client XYZ"
    )
}

@Composable
@Preview(showBackground = true)
fun PreviewEmployeeAddScreen() {
    EmployeeAddScreenPreview()
}
