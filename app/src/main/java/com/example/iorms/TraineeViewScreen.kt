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
import androidx.compose.material3.Divider
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

@Composable
fun TraineeViewScreen(firstName: String, navController: NavController,resourceId: String,role:String, selectedTab: String,apiResponse: ApiResponse, selectedClient: String) {
    var currentTab by remember { mutableStateOf(0) }
    val tabs = listOf("Resource Details")
    var apiResponseBody by remember { mutableStateOf<DetailedViewApiResponse?>(null) }
    var resourceDetails by remember { mutableStateOf<TraineeDetails?>(null) }
    var leadHistory by remember { mutableStateOf<List<LeadsProjectsHistory>?>(null) }
    val gson = Gson()
    val apiResponseBodyJson = gson.toJson(apiResponse)
    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val userResponse = ApiClient.providesApiService().detailedViewOfEmpOrConsul(selectedTab, resourceId)
                Log.e("userResponse", userResponse.toString())
                Log.e("hello",userResponse.body().toString())

                if (userResponse.isSuccessful) {
                    apiResponseBody = userResponse.body()
                    Log.e("apiResponseBody", userResponse.toString())
                    Log.e("apiResponseBody", userResponse.body().toString())
                    if (apiResponseBody != null) {
                        resourceDetails = apiResponseBody!!.traineeDetails
                        leadHistory = apiResponseBody!!.leadsProjectsHistory
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
            Spacer(modifier = Modifier.weight(1f))
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

        Divider(
            color = Color(0xFFE3E3E3),
            modifier = Modifier
                .padding(start = 0.dp, top = 8.dp, end = 0.dp)
                .height(1.dp)
                .fillMaxWidth()
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Back",
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .clickable {
                        navController.navigate("PmcMainScreen/resourceIdValue/$firstName/role/Trainees/$selectedClient/$apiResponseBodyJson")
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
            TabRow(selectedTabIndex = currentTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = currentTab == index,
                        onClick = { currentTab = index },
                        text = {
                            Text(
                                text = title,
                                color = if (currentTab == index) Color.Black else Color.Gray,
                                fontWeight = if (currentTab == index) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        modifier = Modifier.padding(vertical = 0.dp)
                    )
                }
            }

            when (currentTab) {
                0 -> resourceDetails?.let { ResourceDetailsTrainee(it) }
                // Add more cases for other tabs if needed
            }
        }
    }
}

@Composable
fun ResourceDetailsTrainee(resourceDetails: TraineeDetails) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState()).fillMaxWidth().padding(16.dp))
    {

        Row {

            Text(
                text = "Area of Technology",
                fontSize = 14.sp,
                fontFamily = poppinsFontFamily,
                color = Color.Gray,
                modifier = Modifier.weight(1f)
            )
            resourceDetails?.let {
                it.areaOfTechnology?.let { areaOfTechnology ->
                    Text(
                        text = if (resourceDetails?.areaOfTechnology.isNullOrBlank()) "-" else resourceDetails.areaOfTechnology!!,
                        fontSize = 14.sp,
                        fontFamily = poppinsFontFamily,
                        color = Color.Black,
                        // modifier = Modifier.weight(1f)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        Row {
            Text(
                text = "Degree and Specialization",
                fontSize = 14.sp,
                fontFamily = poppinsFontFamily,
                color = Color.Gray,
               modifier = Modifier.weight(1f)
            )
            resourceDetails?.let {
                it.degreeAndSpecialization?.let { degreeAndSpecialization ->
                    Text(
                        text = if (resourceDetails?.degreeAndSpecialization.isNullOrBlank()) "-" else resourceDetails.degreeAndSpecialization!!,
                        fontSize = 14.sp,
                        fontFamily = poppinsFontFamily,
                        color = Color.Black,
                        // modifier = Modifier.weight(1f)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        Row {
            Text(
                text = "College/University",
                fontSize = 14.sp,
                fontFamily = poppinsFontFamily,
                color = Color.Gray,
                modifier = Modifier.weight(1f)
            )}
        Row{
            resourceDetails?.let {
                it.college?.let { college ->
                    Text(
                        text = if(resourceDetails?.college.isNullOrBlank()) "-" else resourceDetails.college!!,
                        fontSize = 14.sp,
                        fontFamily = poppinsFontFamily,
                        color = Color.Black,
                        // modifier = Modifier.weight(1f)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        Row {
            Text(
                text = "Passed out year",
                fontSize = 14.sp,
                fontFamily = poppinsFontFamily,
                color = Color.Gray,
                modifier = Modifier.weight(1f)
            )
            resourceDetails?.let {
                it.passedOutYear?.let { passedOutYear ->
                    Text(
                        text = if(resourceDetails?.passedOutYear.isNullOrBlank()) "-" else resourceDetails.passedOutYear!!,
                        fontSize = 14.sp,
                        fontFamily = poppinsFontFamily,
                        color = Color.Black,
                        // modifier = Modifier.weight(1f)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        Row {
            Text(
                text = "Infomerica Onboarded Date",
                fontSize = 14.sp,
                fontFamily = poppinsFontFamily,
                color = Color.Gray,
                modifier = Modifier.weight(1f)
            )
            resourceDetails?.let {
                it.infomericaOnboardedDate?.let { infomericaOnboardedDate ->
                    Text(
                        text = if(resourceDetails?.infomericaOnboardedDate.isNullOrBlank()) "-" else resourceDetails.infomericaOnboardedDate!!,
                        fontSize = 14.sp,
                        fontFamily = poppinsFontFamily,
                        color = Color.Black,
                        // modifier = Modifier.weight(1f)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        Row {
            Text(
                text = "Experience, if any",
                fontSize = 14.sp,
                fontFamily = poppinsFontFamily,
                color = Color.Gray,
                modifier = Modifier.weight(1f)
            )
        }
        Row{
            resourceDetails?.let {
                it.experience?.let { experience ->
                    Text(
                        text = if(resourceDetails?.experience.isNullOrBlank()) "-" else resourceDetails.experience!!,
                        fontSize = 14.sp,
                        fontFamily = poppinsFontFamily,
                        color = Color.Black,
                        // modifier = Modifier.weight(1f)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        Row {
            Text(
                text = "Notes",
                fontSize = 14.sp,
                fontFamily = poppinsFontFamily,
                color = Color.Gray,
                modifier = Modifier.weight(1f)
            )
        }
        Row{
            resourceDetails?.let {
                it.notes?.let { notes ->
                    Text(
                        text = if(resourceDetails?.notes.isNullOrBlank()) "-" else resourceDetails.notes!!,
                        fontSize = 14.sp,
                        fontFamily = poppinsFontFamily,
                        color = Color.Black,
                        // modifier = Modifier.weight(1f)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        Row {
            Text(
                text = "Infomerica Last Working Date",
                fontSize = 14.sp,
                fontFamily = poppinsFontFamily,
                color = Color.Gray,
                modifier = Modifier.weight(1f)
            )
        }
        Row{
            resourceDetails?.let {
                it.infomericaLastWorkingDate?.let { notes ->
                    Text(
                        text = if(resourceDetails?.infomericaLastWorkingDate.isNullOrBlank()) "-" else resourceDetails.infomericaLastWorkingDate!!,
                        fontSize = 14.sp,
                        fontFamily = poppinsFontFamily,
                        color = Color.Black,
                        // modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewTraineeViewScreen() {
//    TraineeViewScreen(firstName = "PMC", navController = rememberNavController(), role = "", resourceId = "")
//}
