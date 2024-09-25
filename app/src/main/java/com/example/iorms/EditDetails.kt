package com.example.iorms
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.util.Log
import android.widget.DatePicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.iorms.ui.theme.poppinsFontFamily
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditDetails(navController: NavController, firstName: String, resourceId: String, selectedTab: String,apiResponse: ApiResponse, selectedClient: String) {
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
    var resourceDetails by remember { mutableStateOf<EmployeeOrConsultantDetails?>(null) }
    var updateResourceDetailsRequest by remember { mutableStateOf<UpdateResourceDetailsRequest?>(null) }
    var leadHistory by remember { mutableStateOf<List<LeadsProjectsHistory>?>(null) }
    var apiResponseBody by remember { mutableStateOf<DetailedViewApiResponse?>(null) }
    val gson = Gson()
    val apiResponseBodyJson = gson.toJson(apiResponse)
    var selectedTechStackName by remember { mutableStateOf("") }
    var selectedTechStackId by remember { mutableStateOf(0) }
    var selectedRoleName by remember { mutableStateOf("") }
    var selectedRoleId by remember { mutableStateOf(0) }
    var selectedInfomericaLeadName by remember { mutableStateOf("") }
    var selectedInfomericaLeadId by remember { mutableStateOf("") }
    var selectedPositionName by remember { mutableStateOf("") }
    var selectedPositionId by remember { mutableStateOf(0) }
    var selectedLocationName by remember { mutableStateOf("") }
    var selectedLocationId by remember { mutableStateOf(0) }
    // Fetch the data from API ...
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
                        resourceDetails = apiResponseBody!!.EmployeeOrConsultantDetails
                        leadHistory = apiResponseBody!!.leadsProjectsHistory

                        // Update state variables with the fetched data
                        resourceDetails?.let { details ->
                            firstName1 = details.firstName.toString()
                            lastName = details.lastName.toString()
                            resourceId1 = details.resourceId
                            resourceEmail = details.emailId.toString()
                            areaOfTechnology = details.areaOfTechnology.toString()
                            role = details.roleName.toString()
                            infomericaLeadName = details.currentInfomericaLeadName.toString()
                            resourceCurrentPosition = details.resourceCurrentPosition.toString()
                            resourceCurrentLocation = details.resourceCurrentLocation.toString()
                            infomericaOnboardDate = details.infomericaOnboardedDate.toString()
                            visaType = details.visaType ?: ""
                            visaValidityDate = details.visaValidityDate ?: ""
//                            clientName = details.clientName.toString()
//                            nrgSapId = details.nrgSAPId.toString()
//                            nrgWorkerId = details.nrgWorkerId.toString()
//                            clientLeadName = details.currentClientLeadName.toString()
//                            currentProject = details.currentProject.toString()
//                            clientPosition = details.clientPosition.toString()
//                            clientOnboardDate = details.clientOnboardedDate.toString()
//                            clientBillingStartDate = details.clientBillingStartDate.toString()
                            notes1 = details.notes1 ?: ""
                            notes2 = details.notes ?: ""
                            isVisaDetailsChecked = details.visaStatus.equals("YES", true)
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
              //  navController.navigate("PmcMainScreen/resourceIdValue/$firstName/role/$selectedTab")

                onClick = { navController.navigate("PmcMainScreen/resourceIdValue/$firstName/role/$selectedTab/$selectedClient/$apiResponseBodyJson") },
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
                    // .padding(top = 6.dp)
                    .clickable {
                        navController.navigate("PmcMainScreen/resourceIdValue/$firstName/role/$selectedTab/$selectedClient/$apiResponseBodyJson")

                    }
            )
        }





        // Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = firstName1,
            onValueChange = { firstName1 = it },
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
            value = resourceId1,
            onValueChange = { resourceId1 = it },
            label = { Text("Resource ID") },
            modifier = Modifier.fillMaxWidth()
        )

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
            onValueChange = { resourceEmail = it },
            label = { Text("Resource Email ID") },
            modifier = Modifier.fillMaxWidth()
        )

//        OutlinedTextField(
//            value = areaOfTechnology,
//            onValueChange = { areaOfTechnology = it },
//            label = { Text("Area of Technology") },
//            modifier = Modifier.fillMaxWidth()
//        )


        apiResponseBody?.EmployeeOrConsultantDetails?.areaOfTechnology?.let { initialSelectedOption ->
            // Find the initial item based on the tech stack name
            val initialItem = apiResponse.listsForDropDown.P_TECH_STACK_REF
                .find { it.techStackName == initialSelectedOption }
            selectedTechStackName = initialItem?.techStackName.toString()
            selectedTechStackId = initialItem?.techStackId!!

            GenericDropdownForEmp(
                itemList = apiResponse.listsForDropDown.P_TECH_STACK_REF,
                initialSelectedOption = initialSelectedOption,
                getItemDisplay = { it.techStackName },
                getItemValue = { it.techStackId },
                labelToDisplay ="Area of Technology"
            ) { selectedName, selectedId ->
                selectedTechStackName = selectedName
                selectedTechStackId = selectedId as Int    //techstackId
                Log.e("selectedTechStackName",selectedTechStackName)
                Log.e("selectedTechStackId",selectedTechStackId.toString())
            }
        }

//        OutlinedTextField(
//            value = role,
//            onValueChange = { role = it },
//            label = { Text("Role") },
//            modifier = Modifier.fillMaxWidth()
//        )
//ROLE ID
        apiResponseBody?.EmployeeOrConsultantDetails?.roleName?.let { initialSelectedOption ->
            // Find the initial item based on the tech stack name
            val initialItem = apiResponse.listsForDropDown.P_ROLES
                .find { it.roleName == initialSelectedOption }
            selectedRoleName = initialItem?.roleName.toString()
            selectedRoleId = initialItem?.roleId!!

            GenericDropdownForEmp(
                itemList = apiResponse.listsForDropDown.P_ROLES,
                initialSelectedOption = initialSelectedOption,
                getItemDisplay = { it.roleName },
                getItemValue = { it.roleId },
                labelToDisplay ="Role"
            ) { selectedName, selectedId ->
                selectedRoleName = selectedName
                selectedRoleId = selectedId as Int    //roleid
                Log.e("selectedRoleName",selectedRoleName)
                Log.e("selectedRoleId",selectedRoleId.toString())
            }
        }


//        OutlinedTextField(
//            value = infomericaLeadName,
//            onValueChange = { infomericaLeadName = it },
//            label = { Text("Infomerica Lead Name") },
//            modifier = Modifier.fillMaxWidth()
//        )
//        InfomericaLead
        apiResponseBody?.EmployeeOrConsultantDetails?.currentInfomericaLeadName?.let { initialSelectedOption ->
            // Find the initial item based on the tech stack name
            val initialItem = apiResponse.listsForDropDown.P_INTERNAL_LEADS
                .find { it.internalLeadName == initialSelectedOption }
            selectedInfomericaLeadName = initialItem?.internalLeadName.toString()
            selectedInfomericaLeadId = initialItem?.internalLeadId!!

            GenericDropdownForEmp(
                itemList = apiResponse.listsForDropDown.P_INTERNAL_LEADS,
                initialSelectedOption = initialSelectedOption,
                getItemDisplay = { it.internalLeadName },
                getItemValue = { it.internalLeadId },
                labelToDisplay ="Infomerica Lead Name"
            ) { selectedName, selectedId ->
                selectedInfomericaLeadName = selectedName
                selectedInfomericaLeadId = selectedId.toString()
                Log.e("InfomericaLeadName",selectedInfomericaLeadName)
                Log.e("InfomericaLead",selectedInfomericaLeadId)

            }
        }

//        OutlinedTextField(
//            value = resourceCurrentPosition,
//            onValueChange = { resourceCurrentPosition = it },
//            label = { Text("Resource Current Position") },
//            modifier = Modifier.fillMaxWidth()
//        )
        apiResponseBody?.EmployeeOrConsultantDetails?.resourceCurrentPosition?.let { initialSelectedOption ->
            // Find the initial item based on the tech stack name
            val initialItem = apiResponse.listsForDropDown.P_DESIGNATIONS
                .find { it.designationName == initialSelectedOption }
            selectedPositionName = initialItem?.designationName.toString()
            selectedPositionId = initialItem?.designationId!!

            GenericDropdownForEmp(
                itemList = apiResponse.listsForDropDown.P_DESIGNATIONS,
                initialSelectedOption = initialSelectedOption,
                getItemDisplay = { it.designationName },
                getItemValue = { it.designationId },
                labelToDisplay ="Resource Current Position"
            ) { selectedName, selectedId ->
                selectedPositionName = selectedName
                selectedPositionId = selectedId  as Int
                Log.e("selectedPositionName",selectedPositionName)
                Log.e("selectedPositionId",selectedPositionId.toString())

            }
        }
//        OutlinedTextField(
//            value = resourceCurrentLocation,
//            onValueChange = { resourceCurrentLocation = it },
//            label = { Text("Resource Current Location") },
//            modifier = Modifier.fillMaxWidth()
//        )

        apiResponseBody?.EmployeeOrConsultantDetails?.resourceCurrentLocation?.let { initialSelectedOption ->
            // Find the initial item based on the tech stack name
            val initialItem = apiResponse.listsForDropDown.P_LOCATIONS
                .find { it.locationName == initialSelectedOption }
            selectedLocationName = initialItem?.locationName.toString()
            selectedLocationId = initialItem?.locationId!!

            GenericDropdownForEmp(
                itemList = apiResponse.listsForDropDown.P_LOCATIONS,
                initialSelectedOption = initialSelectedOption,
                getItemDisplay = { it.locationName },
                getItemValue = { it.locationId },
                labelToDisplay ="Resource Current Location"
            ) { selectedName, selectedId ->
                selectedLocationName = selectedName
                selectedLocationId = selectedId  as Int
                Log.e("selectedLocationName",selectedLocationName)
                Log.e("selectedPositionId",selectedLocationId.toString())

            }
        }
        DatePickerOutlinedTextField(label = "Infomerica Onboard Date")

        Spacer(modifier = Modifier.height(16.dp))

        // Visa Details
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

        Spacer(modifier = Modifier.height(16.dp))

        // Client Details


        Text(
            text = "Client Details",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
        )

        OutlinedTextField(
            value = clientName,
            onValueChange = { clientName = it },
            label = { Text("Client Name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = nrgSapId,
            onValueChange = { nrgSapId = it },
            label = { Text("NRG SAP ID") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = nrgWorkerId,
            onValueChange = { nrgWorkerId = it },
            label = { Text("NRG Worker ID") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = clientLeadName,
            onValueChange = { clientLeadName = it },
            label = { Text("Client Lead Name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = currentProject,
            onValueChange = { currentProject = it },
            label = { Text("Current Project") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = clientPosition,
            onValueChange = { clientPosition = it },
            label = { Text("Client Position") },
            modifier = Modifier.fillMaxWidth()
        )

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
fun DatePickerOutlinedTextField(label: String) {
    var date by remember { mutableStateOf("") }
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    // Date format as required
    val dateFormatter = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            // Set the calendar to the selected date
            calendar.set(year, month, dayOfMonth)
            // Format the date as per the required format
            date = dateFormatter.format(calendar.time)
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
    )

    OutlinedTextField(
        value = date,
        onValueChange = { date = it },
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth().clickable {
            datePickerDialog.show()
        },
        trailingIcon = {
            IconButton(onClick = { datePickerDialog.show() }) {
                Icon(imageVector = Icons.Default.CalendarToday, contentDescription = "Select date")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResourceTypeDropdown() {
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
            modifier = Modifier
                .width(250.dp) // Match the width of the text field
                .background(Color.White) // Set the background color to white
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> GenericDropdownForEmp(
    itemList: List<T>,
    initialSelectedOption: String,
    getItemDisplay: (T) -> String,
    getItemValue: (T) -> Any,
    labelToDisplay: String,
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
            label = { Text("$labelToDisplay") },
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

