package com.example.iorms

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavController
import com.example.iorms.ui.theme.poppinsFontFamily
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.navigation.compose.rememberNavController


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ViewDetails(navController: NavController,firstName: String, role:String,resourceId: String, selectedTab: String, selectedClient: String,apiResponse: ApiResponse) {
    var selectedSegment by remember { mutableStateOf(0) }
    val tabs = listOf("Resource Details", "Client Details", "History")
    var apiResponseBody by remember { mutableStateOf<DetailedViewApiResponse?>(null) }
    var resourceDetails by remember { mutableStateOf<EmployeeOrConsultantDetails?>(null) }
    var leadHistory by remember { mutableStateOf<List<LeadsProjectsHistory>?>(null) }

    CoroutineScope(Dispatchers.IO).launch {
        try {
            val userResponse = ApiClient.providesApiService().detailedViewOfEmpOrConsul(selectedSegment.toString(),resourceId)
            Log.e("userResponse", userResponse.toString())
            if (userResponse.isSuccessful) {
                apiResponseBody = userResponse.body()
                Log.e("apiResponseBody", apiResponseBody.toString())
                if (apiResponseBody != null) {
                    resourceDetails= apiResponseBody!!.EmployeeOrConsultantDetails
                    Log.e("resource", resourceDetails.toString())
                    leadHistory= apiResponseBody!!.leadsProjectsHistory
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


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
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
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Back",
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .clickable {
                        val gson = Gson()
                        val apiResponseBodyJson = gson.toJson(apiResponse)
                        navController.navigate("PmcMainScreen/resourceIdValue/$firstName/roleValue/$selectedTab/$selectedClient/$apiResponseBodyJson")
                    }
            )
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Resource Profile",
                    fontSize = 16.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.padding(top = 6.dp)
                )
            }
        }

        Divider(
            color = Color(0xFFE3E3E3),
            modifier = Modifier
                .padding(start = 0.dp, top = 8.dp, end = 0.dp)
                .height(1.dp)
                .fillMaxWidth()
        )

        // New section
        Column(modifier = Modifier.padding(top = 16.dp)) {
            Row {
                Text(
                    text = "First Name:",
                    fontSize = 14.sp,
                    fontFamily = poppinsFontFamily,
                    color = Color.Gray,
                    modifier = Modifier.weight(1.5f)
                )
                Text(
                    text = "Last Name:",
                    fontSize = 14.sp,
                    fontFamily = poppinsFontFamily,
                    color = Color.Gray,
                    modifier = Modifier.weight(1f)
                )


            }

            Row {
                resourceDetails?.let {
                    it.firstName?.let { it1 ->
                        Text(
                            text = it1,
                            fontSize = 14.sp,
                            fontFamily = poppinsFontFamily,
                            color = Color.Black,
                            modifier = Modifier.weight(3f)
                        )
                    }
                }

                resourceDetails?.let {
                    it.lastName?.let { it1 ->
                        Text(
                            text = it1,
                            fontSize = 14.sp,
                            fontFamily = poppinsFontFamily,
                            color = Color.Black,
                            modifier = Modifier.weight(2f)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            Row {
                Text(
                    text = "Resource ID:",
                    fontSize = 14.sp,
                    fontFamily = poppinsFontFamily,
                    color = Color.Gray,
                    modifier = Modifier.weight(1.5f)
                )
                Text(
                    text = "Resource Type:",
                    fontSize = 14.sp,
                    fontFamily = poppinsFontFamily,
                    color = Color.Gray,
                    modifier = Modifier.weight(1f)
                )

            }
            Row {
                resourceDetails?.let {
                    Text(
                        text = it.resourceId,
                        fontSize = 14.sp,
                        fontFamily = poppinsFontFamily,
                        color = Color.Black,
                        modifier = Modifier.weight(3f)
                    )
                }

                resourceDetails?.let {
                    it.resourceType?.let { it1 ->
                        Text(
                            text = it1,
                            fontSize = 14.sp,
                            fontFamily = poppinsFontFamily,
                            color = Color.Black,
                            modifier = Modifier.weight(2f)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            Row {
                Text(
                    text = "Resource Email ID:",
                    fontSize = 14.sp,
                    fontFamily = poppinsFontFamily,
                    color = Color.Gray,
                    modifier = Modifier.weight(1f)
                )
            }
            Row{
                resourceDetails?.let {
                    it.emailId?.let { it1 ->
                        Text(
                            text = it1,
                            fontSize = 14.sp,
                            fontFamily = poppinsFontFamily,
                            color = Color.Black,
                            modifier = Modifier.weight(2f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(1.dp))
            Divider(
                color = Color(0xFFE3E3E3),
                modifier = Modifier
                    .padding(start = 0.dp, top = 8.dp, end = 0.dp)
                    .height(1.dp)
                    .fillMaxWidth()
            )
            TabRow(selectedTabIndex = selectedSegment) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedSegment == index,
                        onClick = { selectedSegment = index },
                        text = {
                            Text(
                                text = title,
                                color = if (selectedSegment == index) Color.Black else Color.Gray,
                                fontWeight = if (selectedSegment == index) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        modifier = Modifier.padding(vertical = 0.dp)
                    )
                }
            }
//
            when (selectedSegment) {
                0 -> resourceDetails?.let { ResourceDetails(it) }
                1 -> resourceDetails?.let { ClientDetails(it) }
                2 ->resourceDetails?.let { leadHistory?.let { it1 -> HistoryDetails(it1) } }
            }


        }
    }
}

@Composable
fun ResourceDetails(resourceDetails:EmployeeOrConsultantDetails) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState()).fillMaxWidth().padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Area of Technology",
                fontSize = 14.sp,
                fontFamily = poppinsFontFamily,
                color = Color.Gray,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = if (resourceDetails?.areaOfTechnology.isNullOrBlank()) "-" else resourceDetails.areaOfTechnology!!,
                fontSize = 14.sp,
                fontFamily = poppinsFontFamily,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Role",
                fontSize = 14.sp,
                fontFamily = poppinsFontFamily,
                color = Color.Gray,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = if (resourceDetails?.roleName.isNullOrBlank()) "-" else resourceDetails.roleName!!,
                fontSize = 14.sp,
                fontFamily = poppinsFontFamily,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Infomerica Lead Name",
                fontSize = 14.sp,
                fontFamily = poppinsFontFamily,
                color = Color.Gray,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = if (resourceDetails?.currentInfomericaLeadName.isNullOrBlank()) "-" else resourceDetails.currentInfomericaLeadName!!,
                fontSize = 14.sp,
                fontFamily = poppinsFontFamily,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Billing",
                fontSize = 14.sp,
                fontFamily = poppinsFontFamily,
                color = Color.Gray,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = if (resourceDetails?.billingStatus.isNullOrBlank()) "-" else resourceDetails.billingStatus!!,
                fontSize = 14.sp,
                fontFamily = poppinsFontFamily,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Resource Current Position",
                fontSize = 14.sp,
                fontFamily = poppinsFontFamily,
                color = Color.Gray,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = if (resourceDetails?.resourceCurrentPosition.isNullOrBlank()) "-" else resourceDetails.resourceCurrentPosition!!,
                fontSize = 14.sp,
                fontFamily = poppinsFontFamily,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Resource Current Location",
                fontSize = 14.sp,
                fontFamily = poppinsFontFamily,
                color = Color.Gray,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = if (resourceDetails?.resourceCurrentLocation.isNullOrBlank()) "-" else resourceDetails.resourceCurrentLocation!!,
                fontSize = 14.sp,
                fontFamily = poppinsFontFamily,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Infomerica Onboard Date",
                fontSize = 14.sp,
                fontFamily = poppinsFontFamily,
                color = Color.Gray,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = if (resourceDetails?.infomericaOnboardedDate.isNullOrBlank()) "-" else resourceDetails.infomericaOnboardedDate!!,
                fontSize = 14.sp,
                fontFamily = poppinsFontFamily,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Visa",
                fontSize = 14.sp,
                fontFamily = poppinsFontFamily,
                color = Color.Gray,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = if (resourceDetails?.visaStatus.isNullOrBlank()) "-" else resourceDetails.visaStatus!!,
                fontSize = 14.sp,
                fontFamily = poppinsFontFamily,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Visa Type",
                fontSize = 14.sp,
                fontFamily = poppinsFontFamily,
                color = Color.Gray,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = if (resourceDetails?.visaType.isNullOrBlank()) "-" else resourceDetails.visaType!!,
                fontSize = 14.sp,
                fontFamily = poppinsFontFamily,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Visa Validity Date",
                fontSize = 14.sp,
                fontFamily = poppinsFontFamily,
                color = Color.Gray,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = if (resourceDetails?.visaValidityDate.isNullOrBlank()) "-" else resourceDetails.visaValidityDate!!,
                fontSize = 14.sp,
                fontFamily = poppinsFontFamily,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Notes 1",
                fontSize = 14.sp,
                fontFamily = poppinsFontFamily,
                color = Color.Gray,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = if (resourceDetails?.notes.isNullOrBlank()) "-" else resourceDetails.notes!!,
                fontSize = 14.sp,
                fontFamily = poppinsFontFamily,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Notes 2",
                fontSize = 14.sp,
                fontFamily = poppinsFontFamily,
                color = Color.Gray,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = if (resourceDetails?.notes1.isNullOrBlank()) "-" else resourceDetails.notes1!!,
                fontSize = 14.sp,
                fontFamily = poppinsFontFamily,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Infomerica Last Working Date",
                fontSize = 14.sp,
                fontFamily = poppinsFontFamily,
                color = Color.Gray,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = if (resourceDetails?.infomericaLastWorkingDate.isNullOrBlank()) "-" else resourceDetails.infomericaLastWorkingDate!!,
                fontSize = 14.sp,
                fontFamily = poppinsFontFamily,
                color = Color.Black
            )
        }
    }


}




@Composable
fun ClientDetails(resourceDetails: EmployeeOrConsultantDetails) {
    val hasClients = resourceDetails.clientDetails.isNotEmpty() && resourceDetails.clientDetails[0][0].clientName != null
    var selectedClientIndex by remember { mutableStateOf(if (hasClients) 0 else -1) }
    val listOfClients = if (hasClients) resourceDetails.clientDetails.map { it[0].clientName!! } else emptyList()

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        if (hasClients) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Client Name",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = poppinsFontFamily,
                    color = Color.Black,
                    modifier = Modifier.padding(end = 8.dp)
                )

                var expanded by remember { mutableStateOf(false) }

                Box {
                    Row(
                        modifier = Modifier
                            .clickable(onClick = { if (hasClients) expanded = true })
                            .background(Color.White)
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = listOfClients[selectedClientIndex],
                            fontSize = 14.sp,
                            fontFamily = poppinsFontFamily,
                            color = Color.Black,
                           modifier = Modifier.weight(1f)
                        )
                        Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        listOfClients.forEachIndexed { index, clientName ->
                            DropdownMenuItem(onClick = {
                                selectedClientIndex = index
                                expanded = false
                            }, text = {
                                Text(
                                    text = clientName,
                                    fontSize = 14.sp,
                                    fontFamily = poppinsFontFamily,
                                    color = Color.Black,
                                )
                            })
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            val selectedClient = resourceDetails.clientDetails[selectedClientIndex]

            Column {
                ClientDetailItem(label = "Client Details")
                ClientDetailItem(label = "Client Name", value = selectedClient[0].clientName ?: "-")
                ClientDetailItem(label = "Client Position", value = selectedClient[0].clientPosition ?: "-")
                ClientDetailItem(label = "Client Employee ID", value = "${selectedClient[0].clientEmployeeId}")
                ClientDetailItem(label = "Client Worker ID", value = selectedClient[0].clientWorkerId ?: "-")
                ClientDetailItem(label = "Client Onboarded Date", value = selectedClient[0].clientOnboardedDate ?: "-")
                ClientDetailItem(label = "Client Billing Start Date", value = selectedClient[0].clientBillingStartDate ?: "-")
                selectedClient[0].clientOffBoardedDate?.let { ClientDetailItem(label = "Client Offboarded Date", value = it) }

                Spacer(modifier = Modifier.height(16.dp))

                ClientDetailItem(label = "Project Details:", fontWeight = FontWeight.Bold)
                selectedClient[0].projectDetails.forEachIndexed { index, project ->
                    if (index > 0) {
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    ClientDetailItem(label = "Project Name", value = project.projectName ?: "-")
                    ClientDetailItem(label = "Project Start Date", value = project.projectStartDate ?: "-")
                    ClientDetailItem(label = "Project End Date", value = project.projectEndDate ?: "-")
                    ClientDetailItem(label = "Client Lead Name", value = project.clientLeadName ?: "-")
                }
            }
        } else {
            Text(
                text = "No client information available",
                fontSize = 14.sp,
                fontFamily = poppinsFontFamily,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}

@Composable
fun ClientDetailItem(
    label: String,
    value: String = "",
    fontWeight: FontWeight = FontWeight.Normal,
) {
    val displayValue = if (value.isNullOrBlank()) "-" else value
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                fontSize = 14.sp,
                fontFamily = poppinsFontFamily,
                color = Color.Gray,
                modifier = Modifier.weight(1f)

            )
            Text(
                text = displayValue,
                fontSize = 14.sp,
                fontFamily = poppinsFontFamily,
                color = Color.Black,
                fontWeight = fontWeight,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun HistoryDetails(projects: List<LeadsProjectsHistory>) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        items(projects) { project ->
            ProjectCard(project)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}


@Composable
fun ProjectCard(project: LeadsProjectsHistory) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .fillMaxHeight()
                    .background(Color.White) // Change this color to match the design
            )
            Card(
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp) // Padding to separate the line and the card
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Project Name",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Text(
                        text = if (project.projectName.isNotBlank()) project.projectName else "-",
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Text(
                        text = "Project Start Date",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Text(
                        text = if (project.startDate.isNotBlank()) project.startDate else "-",
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Text(
                        text = "Infomerica Lead Name",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Text(
                        text = if (project.infomericaLeadName.isNotBlank()) project.infomericaLeadName else "-",
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    project.currentClientLeadName?.let {
                        Text(
                            text = "Client Lead Name",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Text(
                            text = if (it.isNotBlank()) it else "-",
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ViewDetailsPreview() {
    val navController = rememberNavController()
    ViewDetails(
        navController = navController,
        firstName = "John",
        role = "Developer",
        resourceId = "12345",
        selectedTab = "Resource Details",
        selectedClient = "ABC Corp",
        apiResponse = ApiResponse(), // Assuming you have a default constructor
    )
}