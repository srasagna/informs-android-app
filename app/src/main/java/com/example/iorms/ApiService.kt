package com.example.iorms

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ApiService {
    @POST("/login")
    suspend fun login(
        @Body loginForm: LoginForm

    ):Response<ApiResponse>
    @GET("resource/client/details")
    suspend fun getResourceDetails(@Query("resourceId") resourceId: String): Response<ResourceDetails>

    @POST("/submit/timesheet")
    suspend fun submitTimeSheet(@Body request: TimeSheetRequest): Response<Unit>

    @GET("/search/with/value")
    suspend fun searchResource(@Query("searchValue") searchValue: String): Response<ResponseBody>

    @GET("/getdata")
    suspend fun gettableData(@Query("client") selectedClient: String,@Query("selectedTab") selectedButton: String): Response<EmployeeResponse>
    @GET("/resourcedetails")
    suspend fun detailedViewOfEmpOrConsul(@Query("selectedTab") selectedTab: String, @Query("resourceId") resourceId: String): Response<DetailedViewApiResponse>

    @PUT("/update/resource/details")
    fun updateResourceDetails(
        @Query("selectedTab") selectedTab: String,
        @Body requestBody: ResourceDetailsRequest
    ): Call<String>

    @POST("/insert/resource")
    fun insertDetails(
        @Body requestBody: ResourceAdd
    ):Call<String>


}
