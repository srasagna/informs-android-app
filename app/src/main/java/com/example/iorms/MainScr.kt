import android.app.DatePickerDialog
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.iorms.ApiClient
import com.example.iorms.ApiResponse
import com.example.iorms.PROJECT_DETAILS
import com.example.iorms.R
import com.example.iorms.ResourceDetails
import com.example.iorms.TimeSheetRequest
import com.example.iorms.ui.theme.poppinsFontFamily
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScr(navController: NavController,resourceId: String){
    var workStatus by remember { mutableStateOf(TextFieldValue()) }
    var impediments by remember { mutableStateOf(TextFieldValue()) }
    var selectedOption by remember { mutableStateOf("Work from Office") }
    var selectedDate by remember { mutableStateOf<Date?>(Date()) }
    var isDatePickerVisible by remember { mutableStateOf(false) }
    var employeeData by remember { mutableStateOf<ResourceDetails?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE
    var isPopupVisible by remember { mutableStateOf(false) }
    var isSubmitEnabled by remember { mutableStateOf(false) }
    var showWarning by remember { mutableStateOf(false) } // Track if the warning should be shown






    Log.e("ResId", "ResId: $resourceId")

    LaunchedEffect(resourceId) {
        if(!resourceId.contains("RESOURCE_ID")){
        try {
            Log.e("API Call", "Attempting to fetch resource details for resourceId: $resourceId")

            val response = ApiClient.providesApiService().getResourceDetails(resourceId)
            if (response.isSuccessful) {

                employeeData = response.body()
                Log.e("EmployeeData", "Fetched employee data: $employeeData")
            } else {
                errorMessage = "Failed to fetch data. Error code: ${response.code()}"
            }
        } catch (e: Exception) {
            errorMessage = e.printStackTrace().toString()
e.printStackTrace()
        } finally {
Log.e("Error",errorMessage.toString())
        }}
        else{
Log.e("resourceId",resourceId)
            // Parsing the input string to extract values
            val resourceIdRegex = "RESOURCE_ID=([A-Za-z0-9]+)".toRegex()
            val firstNameRegex = "FIRST_NAME=([A-Za-z]+)".toRegex()
            val lastNameRegex = "LAST_NAME=([A-Za-z]+)".toRegex()
            val techStackNameRegex = "TECH_STACK_NAME=([A-Za-z]+)".toRegex()
            val projectDetailsRegex = "PROJECT_DETAILS=\\[PROJECT_DETAILS\\(PROJECT_NAME=([^,]+), CLIENT_LEAD_NAME=([^,]+), PROJECT_START_DATE=([^,]+), PROJECT_END_DATE=([^\\)]+)\\)\\]".toRegex()

            val resourceId1 = resourceIdRegex.find(resourceId)?.groups?.get(1)?.value
            val firstName = firstNameRegex.find(resourceId)?.groups?.get(1)?.value
            val lastName = lastNameRegex.find(resourceId)?.groups?.get(1)?.value
            val techStackName = techStackNameRegex.find(resourceId)?.groups?.get(1)?.value
            val projectDetailsMatch = projectDetailsRegex.find(resourceId)

            val projectName = projectDetailsMatch?.groups?.get(1)?.value
            val clientLeadName = projectDetailsMatch?.groups?.get(2)?.value
            val projectStartDate = projectDetailsMatch?.groups?.get(3)?.value
            val projectEndDate = projectDetailsMatch?.groups?.get(4)?.value

            val projectDetails = PROJECT_DETAILS(
                PROJECT_NAME = projectName,
                CLIENT_LEAD_NAME = clientLeadName,
                PROJECT_START_DATE = projectStartDate,
                PROJECT_END_DATE = projectEndDate
            )

            employeeData = ResourceDetails(
                RESOURCE_ID = resourceId1,
                FIRST_NAME = firstName,
                LAST_NAME = lastName,
                TECH_STACK_NAME = techStackName,
                PROJECT_DETAILS = listOf(projectDetails)
            )
            Log.e("check 4","$employeeData")

        }
    }


    Column(modifier = Modifier
        .verticalScroll(rememberScrollState())
        .fillMaxSize()) {
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

            Image(painter = painterResource(id = R.drawable.octicon_search), contentDescription = "serach",
                modifier= Modifier
                    .padding(start = 70.dp, top = 38.dp)
                    .height(25.dp)
                    .width(23.dp)
                    .clickable {
                        val employeeDataString = employeeData?.toString()
//                        navController.navigate("MainScr/$resourceId")
                        val apiResponse = ApiResponse()
                        val gson = Gson()
                        val apiResponseBodyJson = gson.toJson(apiResponse)
                        navController.navigate("searchScreen/$employeeDataString/$apiResponseBodyJson")


                    }
            )

            Column(
                modifier = Modifier.padding(start = 10.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.humbleicons_logout),
                    contentDescription = "Logout",
                    modifier = Modifier
                        .padding(top = 34.dp)
                        .height(27.dp)
                        .width(32.dp)
                        .clickable {
                            navController.navigate("LoginScreen")

                        }

                )

                Text(
                    text = "Logout",
                    fontFamily = poppinsFontFamily,
                    fontSize = 8.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 4.dp)
                )


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
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .padding(top = 20.dp, start = 10.dp, end = 10.dp)
                    .fillMaxWidth()
                    .height(190.dp)
                    .background(Color.White)
                    .border(
                        BorderStroke(2.dp, Color(0xFFE3E3E3)),
                        shape = RoundedCornerShape(5.dp)
                    )
                    .shadow(1.dp)
            ){
                Row (
                    modifier=Modifier.fillMaxWidth()
                ){
                    Text(text = "${employeeData?.FIRST_NAME}",
                        color = Color.Black,
                        fontFamily = poppinsFontFamily,
                        fontSize =20.sp,modifier=Modifier
                            .padding(start=10.dp,top=10.dp))

                }
                Row(
                    modifier=Modifier
                        .fillMaxWidth()
                ){
                    Divider(
                        color = Color(0xFFE3E3E3),
                        modifier = Modifier

                            .padding(top = 50.dp)
                            .height(1.dp)
                            .fillMaxWidth(), thickness = 1.dp)


                }
                Row(
                    modifier= Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ){
                    Column(
                        modifier= Modifier
                            .width(180.dp)
                            .fillMaxHeight()
                            .padding(top = 50.dp)
                    ) {
                        Row(
                            modifier= Modifier
                                .fillMaxWidth()
                                .height(35.dp)
                        ) {
                            Text(text = "Employee ID                  :",
                                fontSize = 12.sp,
                                fontFamily = poppinsFontFamily,
                                modifier=Modifier.padding(start=15.dp,top=10.dp))

                        }
                        Row(
                            modifier= Modifier
                                .fillMaxWidth()
                                .height(35.dp)
                        ) {
                            Text(text = "Current Project             :",
                                fontSize = 12.sp,
                                fontFamily = poppinsFontFamily,
                                modifier=Modifier.padding(start=15.dp,top=10.dp))

                        }
                        Row(
                            modifier= Modifier
                                .fillMaxWidth()
                                .height(35.dp)
                        ) {
                            Text(text = "Project Start Date         :",
                                fontSize = 12.sp,
                                fontFamily = poppinsFontFamily,
                                modifier=Modifier.padding(start=15.dp,top=10.dp))

                           }
                        Row(
                            modifier= Modifier
                                .fillMaxWidth()
                                .height(35.dp)
                        ) {
                            Text(text = "Project Lead Name       :",
                                fontSize = 12.sp,
                                fontFamily = poppinsFontFamily,
                                modifier=Modifier.padding(start=15.dp,top=10.dp))

                        }

                    }
                    Column(
                        modifier = Modifier
                            .padding(top = 50.dp)
                            .fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(35.dp)
                        ) {
                            Text(text = "${employeeData?.RESOURCE_ID}" ,
                                fontSize = 12.sp,
                                fontFamily = poppinsFontFamily,
                                modifier = Modifier.padding(start = 15.dp, top = 10.dp)
                            )

                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(35.dp)
                        ) {
                            Text(text = "${employeeData?.PROJECT_DETAILS?.get(0)?.PROJECT_NAME}",
                                fontSize = 12.sp,
                                fontFamily = poppinsFontFamily,
                                modifier = Modifier.padding(start = 15.dp, top = 10.dp)
                            )

                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(35.dp)
                        ) {
                            Text(text = "${employeeData?.PROJECT_DETAILS?.get(0)?.PROJECT_START_DATE}",
                                fontSize = 12.sp,
                                fontFamily = poppinsFontFamily,
                                modifier = Modifier.padding(start = 15.dp, top = 10.dp)
                            )

                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(35.dp)
                        ) {
                            Text(text = "${employeeData?.PROJECT_DETAILS?.get(0)?.CLIENT_LEAD_NAME}",
                                fontSize = 12.sp,
                                fontFamily = poppinsFontFamily,
                                modifier = Modifier.padding(start = 15.dp, top = 10.dp)
                            )

                        }
                    }




                }

            }

        }
        Row(
            modifier=Modifier
                .fillMaxWidth()
        ){
            Divider(
                color = Color(0xFFE3E3E3),
                modifier = Modifier
                    .padding(start = 16.dp, top = 25.dp, end = 16.dp)
                    .height(1.dp)
                    .fillMaxWidth(), thickness = 1.dp)


        }
        Row(
            modifier= Modifier
                .fillMaxWidth()
                .height(40.dp)
        )
        {
            Column(
                modifier = Modifier
                    .width(150.dp)
                    .height(40.dp)
                    .padding(top = 1.dp)
            ) {
                Text(
                    text = "Select Date  :",
                    fontFamily = poppinsFontFamily,
                    fontSize = 13.sp, modifier = Modifier
                        .padding(start = 25.dp, top = 16.dp)
                )

            }


            Box(
                modifier = Modifier
                    .padding( top = 12.dp)
                    .clickable {
                        isDatePickerVisible = true
                    }
                    .width(200.dp)
                    .height(60.dp)
                    .border(
                        BorderStroke(2.dp, Color(0xFFE3E3E3)),
                        shape = RoundedCornerShape(10.dp)
                    )

            )
            {
                Text(
                    text = selectedDate?.let { formatDate(it) } ?: " ",
                    modifier=Modifier.padding(start=5.dp,top=3.dp),
                    color = Color.Black
                )
                Image(
                    painter = painterResource(id = R.drawable.vector),
                    contentDescription = "vector",
                    modifier = Modifier
                        .padding(start = 170.dp, top = 6.dp)
                        .width(20.3.dp)
                        .height(15.23.dp)
                    //.clickable {  }
                )
                if (isDatePickerVisible) {
                    selectedDate?.let {
                        MaterialDatePicker(
                            initialDate=it,
                            onDismissRequest = {
                                isDatePickerVisible = false
                            },
                            onSelectDate = { date ->
                                selectedDate = date
                                isDatePickerVisible = false
                            }
                        )
                    }
                }

            }
        }
        Row(
            modifier=Modifier
                .fillMaxWidth()
        ){
            Divider(
                color = Color(0xFFE3E3E3),
                modifier = Modifier
                    .padding(start = 16.dp, top = 13.dp, end = 16.dp)
                    .height(1.dp)
                    .fillMaxWidth(), thickness = 1.dp)
        }
        Row(
            modifier = Modifier
                .height(80.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = selectedOption == "Work from Home",
                onClick = {
                    selectedOption = "Work from Home"
                    workStatus = TextFieldValue()
                    impediments = TextFieldValue()
                }
            )
            Text(
                text = "Work from\nHome",
                fontFamily = poppinsFontFamily,
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 0.5.dp)
            )

            //Spacer(modifier = Modifier.width(6.dp))

            RadioButton(
                selected = selectedOption == "Work from Office",
                onClick = {
                    selectedOption = "Work from Office"

                    workStatus = TextFieldValue()
                    impediments = TextFieldValue()
                }
            )

            Text(
                text = "Work from\nOffice ",
                fontFamily = poppinsFontFamily,
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 0.5.dp)
            )

            //Spacer(modifier = Modifier.width(6.dp))

            RadioButton(
                selected = selectedOption == "On Leave",
                onClick = {
                    selectedOption = "On Leave"
                    workStatus = TextFieldValue("")
                    impediments = TextFieldValue("")
                }
            )
            Text(
                text = "On\nLeave",
                fontFamily = poppinsFontFamily,
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 0.5.dp)
            )
        }
        Divider(
            color = Color(0xFFE3E3E3),
            modifier = Modifier
                .padding(start = 16.dp, top = 10.dp, end = 16.dp)
                .height(1.dp)
                .fillMaxWidth(), thickness = 1.dp)



        Row(
            modifier= Modifier
                .fillMaxWidth()
                .height(30.dp)
        ){
            Text(
                text = "Work Status :", fontSize = 12.sp,
                fontFamily = poppinsFontFamily,
                modifier=Modifier.padding(start=25.dp,top=5.dp)
            )

        }
        Box(
            modifier= Modifier
                .padding(start = 10.dp, top = 5.dp, end = 10.dp)
                .fillMaxWidth()
                .height(100.dp)
                .border(
                    BorderStroke(2.dp, Color(0xFFE3E3E3)),
                    shape = RoundedCornerShape(10.dp)
                )

        ){
            TextField(
                value = workStatus,
                onValueChange = {
                    workStatus = it
                    isSubmitEnabled = it.text.isNotBlank() && it.text.length >= 10 // Update submit button state based on the length of text
                    showWarning = it.text.length < 10 // Update showWarning based on the length of text
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 5.dp)
                    .padding(top = 3.dp),
                placeholder = {
                    Text(text = "Type here..", color = Color(0xFFE3E3E3),
                        fontFamily = poppinsFontFamily,)
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    textColor = Color.Black,
                    focusedIndicatorColor = Color(0xffe3e3e3),
                    unfocusedIndicatorColor = Color(0xffe3e3e3)
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                enabled = selectedOption != "On Leave" // Enable only if not On Leave
            )

// Display warning text only if showWarning is true
            if (showWarning && selectedOption != "On Leave") {
                Text(
                    text = "Improper Work Status",
                    fontFamily = poppinsFontFamily,
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 8.dp, bottom = 5.dp)
                )
            }



        }
        Row(
            modifier= Modifier
                .fillMaxWidth()
                .height(30.dp)
        ){
            Text(
                text = "Impediments or Blockers, if any :", fontSize = 12.sp,
                fontFamily = poppinsFontFamily,
                modifier=Modifier.padding(start=25.dp,top=7.dp)
            )

        }
        Box(
            modifier= Modifier
                .padding(start = 10.dp, top = 5.dp, end = 10.dp)
                .fillMaxWidth()
                .height(60.dp)
                .border(
                    BorderStroke(2.dp, Color(0xFFE3E3E3)),
                    shape = RoundedCornerShape(10.dp)
                )

        ){
            TextField(
                value = impediments,
                onValueChange = { impediments = it },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 5.dp)
                    .padding(top = 3.dp),
                placeholder = {
                    Text(text = "Type here..", color = Color(0xFFE3E3E3),
                        fontFamily = poppinsFontFamily,)
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    textColor = Color.Black,
                    focusedIndicatorColor = Color(0xffe3e3e3),
                    unfocusedIndicatorColor = Color(0xffe3e3e3)
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                enabled = selectedOption != "On Leave"
            )

        }
        Button(
            onClick = {
                if (workStatus.text.isNotBlank()) {
                }
                    isPopupVisible = true


                GlobalScope.launch(Dispatchers.IO) {
                    val formattedDate = selectedDate?.let { date ->
                        val localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                        dateFormatter.format(localDate)
                    } ?: ""

                    val timeSheetRequest = TimeSheetRequest(
                        workStatus = workStatus.text,
                        blocker = impediments.text,
                        workType = selectedOption,
                        timesheetDate = formattedDate,
                        createdBy = "${employeeData?.FIRST_NAME}",
                        resourceId = "${employeeData?.RESOURCE_ID}"
                    )

                    try {
                        val response = ApiClient.providesApiService().submitTimeSheet(timeSheetRequest)

                        if (response.isSuccessful) {
                            withContext(Dispatchers.Main) {
                                navController.navigate("SuccessScreen")
                            }
                        } else {
                            Log.e("ResponseError", "API response was not successful: ${response.code()}")
                        }
                    } catch (e: Exception) {
                        Log.e("APIError", "An error occurred during the API call: ${e.message}")
                    }
                }
            },
            modifier = Modifier
                .padding(start = 70.dp)
                .padding(vertical = 20.dp)
                .width(250.dp)
                .padding(horizontal = 60.dp)
                .height(40.dp),
            shape = RoundedCornerShape(7.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD2F6DC)),
            enabled = isSubmitEnabled || selectedOption == "On Leave"

        ) {
            Text(
                text = "Submit",
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp,
                fontFamily = poppinsFontFamily,
                color = Color.Black
            )
        }


    }
    if (isPopupVisible) {
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
                PopupDialog(onDismiss = { isPopupVisible = false })
            }
        }
    }


}


@Composable
fun RadioButtonOption(
    text: String,
    selectedOption: String,
    onSelectedOptionChanged: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = text == selectedOption,
            onClick = { onSelectedOptionChanged(text) }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,

            modifier = Modifier.padding(start = 6.dp)
        )
    }
}
@Composable
fun PopupDialog(
    onDismiss: () -> Unit
) {
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
                modifier= Modifier
                    .height(40.dp)
                    .fillMaxWidth())
                Text(
                    text = "Status Submitted\nSuccessfully",
                    fontFamily = poppinsFontFamily,
                    fontSize = 16.sp, textAlign = TextAlign.Center,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 50.dp, top = 15.dp)
                )
                Button(
                    onClick = { onDismiss() },
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
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MaterialDatePicker(
    initialDate: Date,
    onDismissRequest: () -> Unit,
    onSelectDate: (Date) -> Unit
) {
    val pickerDateState = remember { mutableStateOf(Calendar.getInstance()) }

    val context = LocalContext.current

    DisposableEffect(key1 = context) {
        val today = Calendar.getInstance()
        val minDate = today.clone() as Calendar
        minDate.add(Calendar.DAY_OF_MONTH, -7) // Go back 7 days

        val dialog = DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                pickerDateState.value.set(year, month, dayOfMonth)
                val selectedDate = pickerDateState.value.time
                if (isValidDate(selectedDate)) {
                    onSelectDate(selectedDate)
                } else {
                    // Do nothing for Saturday and Sunday
                }
            },
            pickerDateState.value.get(Calendar.YEAR),
            pickerDateState.value.get(Calendar.MONTH),
            pickerDateState.value.get(Calendar.DAY_OF_MONTH)
        )

        dialog.datePicker.minDate = minDate.timeInMillis

        // Set maximum date to today's date
        dialog.datePicker.maxDate = today.timeInMillis

        // Custom date validation to disable Saturdays and Sundays
        dialog.datePicker.setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
            val selectedDate = Calendar.getInstance().apply {
                set(year, monthOfYear, dayOfMonth)
            }.time
            if (!isValidDate(selectedDate)) {
                // Do nothing for Saturday and Sunday
                // Reset the date picker to the previous valid date
                dialog.updateDate(
                    pickerDateState.value.get(Calendar.YEAR),
                    pickerDateState.value.get(Calendar.MONTH),
                    pickerDateState.value.get(Calendar.DAY_OF_MONTH)
                )
            } else {
                pickerDateState.value.set(year, monthOfYear, dayOfMonth)
            }
        }

        dialog.setOnDismissListener {
            onDismissRequest()
        }

        dialog.show()

        onDispose {
            dialog.dismiss()
        }
    }
}

fun isValidDate(date: Date): Boolean {
    val calendar = Calendar.getInstance()
    calendar.time = date
    val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
    return dayOfWeek != Calendar.SATURDAY && dayOfWeek != Calendar.SUNDAY
}

fun formatDate(date: Date): String {
    val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return sdf.format(date)
}


