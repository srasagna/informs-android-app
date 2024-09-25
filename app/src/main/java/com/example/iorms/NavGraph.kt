package com.example.iorms

import MainScr
import PmcMainScreen
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Nav() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "LoginScreen") {
        composable(route = "LoginScreen") {
            LoginScreen(navController = navController)
        }
        composable(route = "MainScr/{resourceId}") { backStackEntry ->
            val resourceId = backStackEntry.arguments?.getString("resourceId") ?: "defaultResourceId"
            MainScr(navController = navController, resourceId = resourceId)
        }

        composable(route = "PmcMainScreen/{resourceId}/{firstName}/{role}/{selectedTab}/{selectedClient}/{data}") { backStackEntry ->
            val resourceId = backStackEntry.arguments?.getString("resourceId") ?: "defaultResourceId"
            val firstName = backStackEntry.arguments?.getString("firstName") ?: "PMC"
            val role = backStackEntry.arguments?.getString("role") ?: "PMC"
            val selectedTab = backStackEntry.arguments?.getString("selectedTab") ?: "employee"
            val dataJson = backStackEntry.arguments?.getString("data") ?: "{}"
            val selectedClient = backStackEntry.arguments?.getString("selectedClient") ?: "INFOMERICA"
            val gson = Gson()
            val apiResponseBody = gson.fromJson(dataJson, ApiResponse::class.java)

            PmcMainScreen(navController = navController, resourceId = resourceId, firstName = firstName, role = role, selectedTab = selectedTab, selectedClient = selectedClient, apiResponseBody = apiResponseBody)
        }

        composable(route = "SearchScreen/{employeeDataString}/{data}") { backStackEntry ->
            val empDataString = backStackEntry.arguments?.getString("employeeDataString") ?: ""
            val gson = Gson()
            val dataJson = backStackEntry.arguments?.getString("data") ?: "{}"
            val apiResponseBody = gson.fromJson(dataJson, ApiResponse::class.java)

            SearchScreen(navController = navController, empDataString = empDataString,apiResponseBody= apiResponseBody )
        }
        composable(route = "ViewDetails/{firstName}/{role}/{resourceId}/{selectedTab}/{selectedClient}/{data}") { backStackEntry ->
            val firstName = backStackEntry.arguments?.getString("firstName") ?: "PMC"
            val resourceId = backStackEntry.arguments?.getString("resourceId") ?: ""
            val selectedTab = backStackEntry.arguments?.getString("selectedTab") ?: ""
            val role = backStackEntry.arguments?.getString("role") ?: "PMC"
            val dataJson = backStackEntry.arguments?.getString("data") ?: "{}"
            val selectedClient = backStackEntry.arguments?.getString("selectedClient") ?: "INFOMERICA"
            val gson = Gson()
            val apiResponseBody = gson.fromJson(dataJson, ApiResponse::class.java)
            if (resourceId.isNotEmpty() && selectedTab.isNotEmpty()) {
                ViewDetails(navController = navController, firstName = firstName,role = role, resourceId = resourceId, selectedTab = selectedTab, selectedClient = selectedClient, apiResponse = apiResponseBody)
            }
        }
        composable(route = "EditDetails/{firstName}/{resourceId}/{selectedTab}/{selectedClient}/{data}") { backStackEntry ->
            val firstName = backStackEntry.arguments?.getString("firstName") ?: "PMC"
            val resourceId = backStackEntry.arguments?.getString("resourceId") ?: ""
            val selectedTab = backStackEntry.arguments?.getString("selectedTab") ?: ""
            val selectedClient = backStackEntry.arguments?.getString("selectedClient") ?: "INFOMERICA"
            val dataJson = backStackEntry.arguments?.getString("data") ?: "{}"
            val gson = Gson()
            val apiResponseBody = gson.fromJson(dataJson, ApiResponse::class.java)

            if (resourceId.isNotEmpty() && selectedTab.isNotEmpty()) {
                EditDetails(navController = navController, firstName = firstName, resourceId = resourceId, selectedTab = selectedTab,selectedClient = selectedClient, apiResponse = apiResponseBody)
            }
        }
//
        composable(route = "TraineeViewScreen/{firstName}/{role}/{resourceId}/{selectedTab}/{selectedClient}/{data}") { backStackEntry ->
            val firstName = backStackEntry.arguments?.getString("firstName") ?: "defaultFirstName"
            val resourceId = backStackEntry.arguments?.getString("resourceId") ?: "defaultResourceId"
            val role = backStackEntry.arguments?.getString("role") ?: "PMC"
            val selectedTab = backStackEntry.arguments?.getString("selectedTab") ?: ""
            val selectedClient = backStackEntry.arguments?.getString("selectedClient") ?: "INFOMERICA"
            val dataJson = backStackEntry.arguments?.getString("data") ?: "{}"
            val gson = Gson()
            val apiResponseBody = gson.fromJson(dataJson, ApiResponse::class.java)
            TraineeViewScreen(navController = navController, firstName = firstName, resourceId = resourceId, role = role, selectedTab = selectedTab, selectedClient = selectedClient, apiResponse = apiResponseBody)
        }
        composable(route = "TraineeEditScreen/{firstName_1}/{role}/{resourceId}/{selectedTab}/{selectedClient}/{data}") { backStackEntry ->
            val firstName_1 = backStackEntry.arguments?.getString("firstName_1") ?: "defaultFirstName"
            val resourceId = backStackEntry.arguments?.getString("resourceId") ?: "defaultResourceId"
            val role = backStackEntry.arguments?.getString("role") ?: "PMC"
            val selectedTab = backStackEntry.arguments?.getString("selectedTab") ?: ""
            val selectedClient = backStackEntry.arguments?.getString("selectedClient") ?: "INFOMERICA"
            val dataJson = backStackEntry.arguments?.getString("data") ?: "{}"
            val gson = Gson()
            val apiResponseBody = gson.fromJson(dataJson, ApiResponse::class.java)
            if (resourceId.isNotEmpty() && selectedTab.isNotEmpty()) {
                TraineeEditScreen(navController = navController, firstName = firstName_1, resourceId = resourceId, selectedTab = selectedTab, selectedClient = selectedClient, role = role,apiResponse=apiResponseBody)
            }
        }
        composable(
            "EmployeeAddScreen/{firstName}/{selectedTab}/{selectedClient}/{data}",
            arguments = listOf(
                navArgument("firstName") { type = NavType.StringType },
                navArgument("selectedTab") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val firstName = backStackEntry.arguments?.getString("firstName") ?: ""
            val selectedTab = backStackEntry.arguments?.getString("selectedTab") ?: "Employees"
            val selectedClient = backStackEntry.arguments?.getString("selectedClient") ?: "INFOMERICA"
            val dataJson = backStackEntry.arguments?.getString("data") ?: "{}"
            val gson = Gson()
            val apiResponseBody = gson.fromJson(dataJson, ApiResponse::class.java)
            EmployeeAddScreen(navController, firstName, selectedTab, selectedClient = selectedClient, apiResponseBody=apiResponseBody)
        }
        composable(
            "TraineeAddScreen/{firstName}/{selectedTab}/{selectedClient}/{data}",
            arguments = listOf(
                navArgument("firstName") { type = NavType.StringType },
                navArgument("selectedTab") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val firstName = backStackEntry.arguments?.getString("firstName") ?: ""
            val selectedTab = backStackEntry.arguments?.getString("selectedTab") ?: "Trainees"
            val dataJson = backStackEntry.arguments?.getString("data") ?: "{}"
            val gson = Gson()
            val selectedClient = backStackEntry.arguments?.getString("selectedClient") ?: "INFOMERICA"
            val apiResponseBody = gson.fromJson(dataJson, ApiResponse::class.java)
            TraineeAddScreen(navController, firstName, selectedTab,selectedClient = selectedClient,apiResponseBody=apiResponseBody)
        }

    }
}
