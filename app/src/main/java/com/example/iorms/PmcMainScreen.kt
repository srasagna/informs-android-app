import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.iorms.ApiClient
import com.example.iorms.EmployeeResponse
import com.example.iorms.R
import com.example.iorms.Resource
import com.example.iorms.TableResponse
import com.example.iorms.ui.theme.poppinsFontFamily
import kotlinx.coroutines.launch
import androidx.compose.runtime.Composable
import com.example.iorms.ApiResponse
import com.google.gson.Gson


@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PmcMainScreen(navController: NavController, resourceId:String, firstName:String,role:String,selectedTab: String, apiResponseBody: ApiResponse, selectedClient: String) {
    var selectedClientInScreen by rememberSaveable { mutableStateOf(selectedClient) }
//    var selectedButton by rememberSaveable { mutableStateOf("Employees") }
//    var selectedTab by remember { mutableStateOf("employee") }
    Log.d("Hello",apiResponseBody.toString())
    var tab by rememberSaveable { mutableStateOf("Employees") }
    var currentTab by remember { mutableStateOf(selectedTab) }
    val gson = Gson()
    var selectedButton by remember(currentTab) {
        mutableStateOf(when (currentTab) {
            "Employees" -> "Employees"
            "Consultants" -> "Consultants"
            "Trainees" -> "Trainees"
            else -> "Employees"
        })
    }

    val coroutineScope = rememberCoroutineScope()
    var apiResponse by remember { mutableStateOf<EmployeeResponse?>(null) }

    LaunchedEffect(selectedButton, selectedClientInScreen) {
        coroutineScope.launch {
            try {
                val response = ApiClient.providesApiService().gettableData(selectedClientInScreen,selectedButton)
                Log.e("ApiBody11", response.toString())
                    Log.e("ApiBody22", response.body().toString())
                if (response.isSuccessful) {
                    apiResponse= response.body()
                    // Log the response body content
                    Log.d("ApiResponse", "Response: $apiResponse")
                    if (apiResponse != null) {
                        Log.e("Total Response",apiResponse.toString())
                        Log.e("test", apiResponse!!.tableResponse.toString())
                        Log.d("ApiResponse", apiResponse!!.tableResponse.employees.toString())
                        Log.d("COnsultants",apiResponse!!.tableResponse.consultants.toString())
                    }

                } else {
                    // Handle unsuccessful response
                    apiResponse = null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // Handle error, maybe show a Toast or something similar
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header Section
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(81.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.your_app_logo),
                    contentDescription = "App Logo",
                    modifier = Modifier
                        .padding(top = 20.dp, start = 20.dp)
                        .size(100.dp)
                )
                Text(
                    text = "INFORMS",
                    fontSize = 20.sp,
                    fontFamily = poppinsFontFamily,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(top = 30.dp, start = 8.dp)
                )
                Spacer(modifier = Modifier.weight(1f)) // Spacer to push the following column to the end
                Column(
                    modifier = Modifier.padding(end = 16.dp, top = 25.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = firstName,
                            fontSize = 12.sp,
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
            Divider(
                modifier = Modifier
                    .padding(top = 1.dp)
                    .height(1.dp)
                    .fillMaxWidth()
            )
            // Client Selection Section
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Select Client: ",
                    fontSize = 14.sp,
                    fontFamily = poppinsFontFamily,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(start = 15.dp, top = 19.dp)
                )
                DropDown(
                    itemList = listOf("INFOMERICA", "NRG"),
                    selectedItem = selectedClientInScreen,
                    onItemSelected = { selectedClientInScreen = it }
                )
            }



            // Displaying content based on selected client
            if (selectedClientInScreen == "INFOMERICA") {
                Divider(
                    color = Color(0xFFE3E3E3),
                    modifier = Modifier
                        .padding(start = 8.dp, top = 1.dp, end = 12.dp)
                        .height(1.dp)
                        .fillMaxWidth()
                )
                apiResponse?.tableResponse?.let { countsResponse ->
                    RowWithBadgedButtonsInf(
                        initialSelectedButton = selectedButton,
                        data = countsResponse,
                        onButtonSelected = { selected ->
                            selectedButton = selected
                            Log.e("selectedButton",selectedButton)
                            currentTab = selectedButton
                            Log.e("tab",currentTab)
                        },
                        callRecyclerViewFunction = { data, selectedTab ->
                            CallRecyclerViewFunction(data, selectedTab, navController, firstName,apiResponseBody,selectedClientInScreen)
                        }
                    )
                }


            }
           else{


                Divider(
                    color = Color(0xFFE3E3E3),
                    modifier = Modifier
                        .padding(start = 8.dp, top = 2.dp, end = 12.dp)
                        .height(1.dp)
                        .fillMaxWidth()
                )

                apiResponse?.tableResponse?.let { countsResponse ->
                    RowWithBadgedButtonsNrg(
                        initialSelectedButton = selectedButton,
                        data = countsResponse,
                        onButtonSelected = { selected ->
                            selectedButton = selected
                            currentTab = selectedButton
                        },
                        callRecyclerViewFunction = { data, selectedTab ->
                            CallRecyclerViewFunction(
                                data,
                                selectedTab,
                                navController,
                                firstName,
                                apiResponseBody,selectedClientInScreen
                            )
                        }
                    )
                }


            }
        }
        val empDataString = "resourceId:$resourceId, role:$role, firstName:$firstName, selectedTab:$currentTab, selectedClient:$selectedClientInScreen"
        // Floating Action Button
        Log.e("empDataString",empDataString)
        FloatingActionButton(
            onClick = {


                val apiResponseBodyJson = gson.toJson(apiResponseBody)
                navController.navigate("SearchScreen/$empDataString/$apiResponseBodyJson") {
                    // Include role-specific arguments based on the user's role
                    launchSingleTop = true
                    popUpTo("PmcMainScreen") {
                        inclusive = false
                    }
                }
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            shape = CircleShape,
            containerColor = Color(0xFF4CAF50)
        ) {
            Icon(Icons.Rounded.Search, contentDescription = null, tint = Color.White)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CallRecyclerViewFunction(
    data: TableResponse,
    selectedTab: String,
    navController: NavController,
    firstName: String,
    apiResponseBody: ApiResponse,
    selectedClient: String
) {
    var search by remember { mutableStateOf(TextFieldValue()) }
    val filteredEmployees = remember(search.text, data.employees) {
        data.employees?.filter {
            it.FIRST_NAME.contains(search.text, ignoreCase = true) ||
                    it.LAST_NAME.contains(search.text, ignoreCase = true) ||
                    it.RESOURCE_NAME?.contains(search.text, ignoreCase = true) ?: false ||
                    it.RESOURCE_ID.contains(search.text, ignoreCase = true) ||
                    it.EMAIL_ID.contains(search.text, ignoreCase = true) ||
                    it.TECH_STACK_NAME?.contains(search.text, ignoreCase = true) ?: false
        } ?: emptyList()
    }

    val filteredConsultants = remember(search.text, data.consultants) {
        data.consultants?.filter {
            it.FIRST_NAME.contains(search.text, ignoreCase = true) ||
                    it.LAST_NAME.contains(search.text, ignoreCase = true) ||
                    it.RESOURCE_NAME?.contains(search.text, ignoreCase = true) ?: false ||
                    it.RESOURCE_ID.contains(search.text, ignoreCase = true) ||
                    it.EMAIL_ID.contains(search.text, ignoreCase = true) ||
                    it.TECH_STACK_NAME?.contains(search.text, ignoreCase = true) ?: false
        } ?: emptyList()
    }

    val filteredTrainees = remember(search.text, data.trainees) {
        data.trainees?.filter {
            (it.FIRST_NAME?.contains(search.text, ignoreCase = true) == true) ||
                    (it.LAST_NAME?.contains(search.text, ignoreCase = true) == true) ||
                    (it.RESOURCE_NAME?.contains(search.text, ignoreCase = true) == true) ||
                    (it.RESOURCE_ID?.contains(search.text, ignoreCase = true) == true) ||
                    (it.EMAIL_ID?.contains(search.text, ignoreCase = true) == true) ||
                    (it.TECH_STACK_NAME?.contains(search.text, ignoreCase = true) == true)
        } ?: emptyList()
    }


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(0.8f)
                .border(
                    width = 0.2.dp,
                    color = Color.Gray,
                    shape = RoundedCornerShape(30.dp)
                )
                .height(48.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                    tint = Color(0xFF988D8D),
                    modifier = Modifier
                        .padding(start = 16.dp, end = 0.dp)
                        .size(20.dp)
                )
                TextField(
                    value = search,
                    onValueChange = { search = it },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(end = 8.dp),
                    placeholder = {
                        Text(
                            text = "Search",
                            fontFamily = poppinsFontFamily,
                            color = Color(0xFF988D8D),
                            fontSize = 12.sp
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        textColor = Color.Black,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search
                    ),
                )
            }

        }
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier
                .clickable {
                    val gson = Gson()
                    val apiResponseBodyJson= gson.toJson(apiResponseBody)
                    if (selectedTab == "Employees") {
                        val route = "EmployeeAddScreen/$firstName/Employees/$selectedClient/$apiResponseBodyJson"
                        navController.navigate(route)
                    } else if (selectedTab == "Trainees") {
                        val route = "TraineeAddScreen/$firstName/Trainees/$selectedClient/$apiResponseBodyJson"
                        navController.navigate(route)
                    }
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.AddBox,
                contentDescription = "Add Icon",
                tint = Color(0xFF4CAF50),
                modifier = Modifier
                    .size(28.dp)
            )
            Text(
                text = "Add",
                fontFamily = poppinsFontFamily,
                fontSize = 8.sp,
                color = Color.Black
            )
        }

    }
    Divider(
        color = Color(0xFFE3E3E3),
        modifier = Modifier
            .padding(start = 8.dp, top = 2.dp, end = 12.dp)
            .height(1.dp)
            .fillMaxWidth()
    )
    LazyColumn(modifier = Modifier
        .padding(vertical = 4.dp)
        .background(Color.White)) {

        if (selectedTab == "Employees") {
            filteredEmployees?.let { employees ->
                itemsIndexed(employees) { index, employee ->
                    ListItem(selectedTab = "Employee", employee = employee, serialNumber = index + 1, navController = navController, firstName = firstName,apiResponseBody=apiResponseBody, selectedClient = selectedClient)
                    Divider(
                        color = Color(0xFFE3E3E3),
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                    )
                }
            }
        }
        if (selectedTab == "Consultants") {
            filteredConsultants?.let { consultants ->
                itemsIndexed(consultants) { index, employee ->
                    ListItem(selectedTab = "Consultants", employee = employee, serialNumber = index + 1, navController = navController, firstName = firstName,apiResponseBody=apiResponseBody, selectedClient = selectedClient)
                    Divider(
                        color = Color(0xFFE3E3E3),
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                    )
                }
            }
        }
        if (selectedTab == "Trainees") {
            filteredTrainees?.let { employees ->
                itemsIndexed(employees) { index, employee ->
                    ListItem(selectedTab = "Trainees", employee = employee, serialNumber = index + 1, navController = navController, firstName = firstName,apiResponseBody=apiResponseBody, selectedClient = selectedClient)
                    Divider(
                        color = Color(0xFFE3E3E3),
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun ListItem(selectedTab: String, firstName: String, employee: Resource, serialNumber: Int, navController: NavController,apiResponseBody:ApiResponse,selectedClient: String) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val name = if (!employee.FIRST_NAME.isNullOrEmpty() || !employee.LAST_NAME.isNullOrEmpty()) {
            "${employee.FIRST_NAME} ${employee.LAST_NAME}"
        } else {
            employee.RESOURCE_NAME
        }

        val gson = Gson()
        val apiResponseBodyJson = gson.toJson(apiResponseBody)
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "$serialNumber. ",
                    fontSize = 14.sp
                )
                if (name != null) {
                    Text(
                        text = name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                employee.TECH_STACK_NAME?.let {
                    Text(
                        text = it,
                        fontSize = 9.sp,
                        modifier = Modifier
                            .background(Color(0xFFE0D7FA), shape = RoundedCornerShape(12.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
            Text(
                text = employee.RESOURCE_ID,
                fontSize = 14.sp,
                color = Color.Gray
            )
            Text(
                text = employee.EMAIL_ID,
                color = Color.Gray,
                fontSize = 14.sp
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            IconButton(onClick = {
                Log.e("Navigation", "Navigating to ViewDetails with firstName: $firstName, resourceId: ${employee.RESOURCE_ID}, selectedTab: $selectedTab")

                when (selectedTab) {
                    "Trainees" -> navController.navigate("TraineeViewScreen/$firstName/roleValue/${employee.RESOURCE_ID}/$selectedTab/$selectedClient/$apiResponseBodyJson")
                    "Consultants" -> navController.navigate("ViewDetails/$firstName/roleValue/${employee.RESOURCE_ID}/$selectedTab/$selectedClient/$apiResponseBodyJson")
                    "Employee" -> navController.navigate("ViewDetails/$firstName/roleValue/${employee.RESOURCE_ID}/$selectedTab/$selectedClient/$apiResponseBodyJson")
                }
            }, modifier = Modifier.size(20.dp)) {
                Icon(
                    Icons.Default.Visibility,
                    contentDescription = "View",
                    tint = Color(0xFF4CAF50)
                )
            }


            IconButton(onClick = {
                when (selectedTab) {
                    "Trainees" -> navController.navigate("TraineeEditScreen/$firstName/roleValue/${employee.RESOURCE_ID}/$selectedTab/$selectedClient/$apiResponseBodyJson")
                     "Consultants" -> navController.navigate("EditDetails/$firstName/${employee.RESOURCE_ID}/$selectedTab/$selectedClient/$apiResponseBodyJson")
                    "Employee"-> navController.navigate("EditDetails/$firstName/${employee.RESOURCE_ID}/$selectedTab/$selectedClient/$apiResponseBodyJson")
                }
            }
                ,modifier = Modifier.size(20.dp)) {
                Icon(
                    Icons.Default.Edit,
                    contentDescription = "Edit",
                    tint = Color(0xFF4CAF50)
                )
            }
//            IconButton(onClick = { /* Add your archive logic here */ }, modifier = Modifier.size(20.dp)) {
//                Icon(
//                    Icons.Default.Archive,
//                    contentDescription = "Archive",
//                    tint = Color.Red
//                )
//            }
        }
    }
}




@Composable
fun DropDown(
    itemList: List<String>,
    selectedItem: String,
    onItemSelected: (selectedItem: String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .padding(vertical = 6.dp)
            .padding(start = 17.dp)
    ) {
        OutlinedButton(
            onClick = { expanded = true },
            border = BorderStroke(1.dp, Color.LightGray),
            modifier = Modifier.width(230.dp)
        ) {
            Text(
                text = selectedItem,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically),
                textAlign = TextAlign.Center,
                color = Color.Black
            )
            Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = Color.Gray)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(230.dp)
                .background(Color.White, RoundedCornerShape(20.dp))
        ) {
            itemList.forEach { item ->
                val isSelected = item == selectedItem

                DropdownMenuItem(
                    text = {
                        Text(
                            text = item,
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.CenterHorizontally),
                            textAlign = TextAlign.Center,
                            color = if (isSelected) Color.White else Color.Black
                        )
                    },
                    onClick = {
                        expanded = false
                        onItemSelected(item)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(if (isSelected) Color(0xFF0d6efd) else Color.Transparent)
                )
            }
        }
    }
}

@Composable
fun RowWithBadgedButtonsNrg(
    initialSelectedButton: String,
    data: TableResponse,
    onButtonSelected: (String) -> Unit,
    callRecyclerViewFunction: @Composable (TableResponse, String) -> Unit
) {
    var selectedButton by remember { mutableStateOf("Employees") }

    val buttons = listOf(
        "Employees" to data.counts.Active.Employee,
        "Consultants" to data.counts.Active.Consultant,
       // "Onboarding's" to data.counts.Active.OnboardingTracker,
       // "Reports" to 0
    )

    Column {
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState())
                .fillMaxWidth()
                .height(55.dp)
                .padding(horizontal = 70.dp), // Add padding to start and end
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            buttons.forEach { (text, count) ->
                ButtonWithBadge(
                    text = text,
                    badgeCount = count ?: 0,  // Ensure a non-null badge count
                    isSelected = selectedButton == text,
                    data = data
                ) {
                    selectedButton = text
                    onButtonSelected(text)
                }
            }
        }

        Divider(
            color = Color(0xFFE3E3E3),
            modifier = Modifier
                .padding(start = 8.dp, top = 1.dp, end = 12.dp)
                .height(1.dp)
                .fillMaxWidth()
        )

        // Call the RecyclerView function for the selected button
        callRecyclerViewFunction(data, selectedButton)
    }
}

@Composable
fun RowWithBadgedButtonsInf(
    initialSelectedButton: String,
    data: TableResponse,
    onButtonSelected: (String) -> Unit,
    callRecyclerViewFunction: @Composable (TableResponse, String) -> Unit
) {
    var selectedButton by remember { mutableStateOf(initialSelectedButton) }

    val buttons = listOf(
        "Employees" to data.counts.Active.Employee,
        "Consultants" to data.counts.Active.Consultant,
        "Trainees" to data.counts.Active.Trainee,
        //"Archived" to 0
    )

    Column {

        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState())
                .fillMaxWidth()
                .height(55.dp)
                .padding(horizontal = 16.dp), // Add padding to start and end
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        )
        {
            buttons.forEach { (text, count) ->
                ButtonWithBadge(
                    text = text,
                    badgeCount = count,
                    isSelected = selectedButton == text,
                    data = data
                ) {
                    selectedButton = text
                    onButtonSelected(text)
                }

            }
        }


        Divider(
            color = Color(0xFFE3E3E3),
            modifier = Modifier
                .padding(start = 8.dp, top = 2.dp, end = 12.dp)
                .height(1.dp)
                .fillMaxWidth()
        )

        // Call the RecyclerView function for the selected button
        callRecyclerViewFunction(data, selectedButton)
    }
}



@Composable
fun ButtonWithBadge(
    text: String,
    badgeCount: Int,
    isSelected: Boolean,
    data: TableResponse,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(120.dp)
            .padding(vertical = 12.dp, horizontal = 2.dp)
            .clickable { onClick() }
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp) // Adjust height for better display
                .shadow(elevation = 0.dp, shape = RoundedCornerShape(12.dp)),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isSelected) Color(0xFFD2F6DC) else Color.White
            )
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = text,
                    fontSize = 10.sp,
                    fontFamily = poppinsFontFamily,
                    color = Color.Black,
                    textAlign = TextAlign.Center,

                    )
                }
            }

                if (badgeCount > 0) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .size(width = 20.dp, height = 16.dp)
                            .background(color = Color(0xFFB643FD), shape = CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = badgeCount.toString(),
                            color = Color.White,
                            fontSize = 9.sp,
                            fontFamily = poppinsFontFamily
                        )
                    }
                }

        }
    }










//@Preview(showBackground = true)
//@Composable
//fun MainPreview() {
//    val navController = rememberNavController()
//    PmcMainScreen(navController, resourceId = "mockResourceId", firstName = "P M C", role = "", selectedTab = "", apiResponseBody=)
//}
//







