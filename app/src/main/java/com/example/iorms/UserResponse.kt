package com.example.iorms

data class ApiResponse(
    val p_client_names: Any?= null,
    val loginResponse: LoginResponse= LoginResponse(),
    val p_resource_ids: Any?? = null,
    val p_client_resource_ids: Any? = null,
    val p_internal_leads: Any? = null,
    val listsForDropDown: ListsForDropDown= ListsForDropDown(),
    val P_OUT_MESSAGE: String? = null
)

data class LoginResponse(
    val email_id: String? = null,
    val RoleName: String? = null,
    val Resource_Id: String? = null,
    val FirstName: String? = null,
    val LastName: String? = null,
)


data class ListsForDropDown(
    val P_TECH_STACK_REF: List<TechStackRef> = emptyList(),
    val P_DESIGNATIONS: List<Designation> = emptyList(),
    val P_ROLES: List<Role> = emptyList(),
    val P_LOCATIONS: List<Location> = emptyList(),
    val P_CLIENT_NAMES: List<ClientName> = emptyList(),
    val P_CURRENT_CLIENT_LEADS: List<ClientLead> = emptyList(),
    val P_CLIENT_POSITIONS: List<ClientPosition> = emptyList(),
    val P_INTERNAL_LEADS: List<InternalLead> = emptyList(),
    val P_RESOURCE_ID: List<ResourceID> = emptyList(),
    val P_CLIENT_RESOURCE_IDS: List<ClientResource> = emptyList(),
    val P_ONBOARDING_PROCESS_RESOURCES: List<OnboardingProcessResource> = emptyList()
)

data class TechStackRef(
    val techStackId: Int,
    val techStackName: String
)

data class Designation(
    val designationId: Int,
    val designationName: String
)

data class Role(
    val roleId: Int,
    val roleName: String
)

data class Location(
    val locationId: Int,
    val locationName: String
)

data class ClientName(
    val clientId: Int,
    val clientName: String
)

data class ClientLead(
    val clientLeadName: String
)

data class ClientPosition(
    val clientPosition: String?
)

data class InternalLead(
    val internalLeadId: String,
    val internalLeadName: String
)

data class ResourceID(
    val resourceId: String,
    val name: String
)

data class ClientResource(
    val resourceId: String,
    val name: String
)

data class OnboardingProcessResource(
    val name: String
)


//_________________________________


data class UserResponse(
    val email_id: String?,
    val RoleName: String?,
    val Resource_Id: String?,
    val FirstName: String?,
    val LastName: String?
)
//data class ApiResponse(
//    val loginResponse: UserResponse,
//    val P_OUT_MESSAGE: String?
//)



data class ResourceDetails(
    val RESOURCE_ID: String?,
    val FIRST_NAME: String?,
    val LAST_NAME: String?,
    val TECH_STACK_NAME: String?,
    val PROJECT_DETAILS : List<PROJECT_DETAILS>
)

data class PROJECT_DETAILS(
    val PROJECT_NAME :String?,
    val CLIENT_LEAD_NAME: String?,
    val PROJECT_START_DATE: String?,
    val PROJECT_END_DATE: String?
)

data class TimeSheetRequest(
    val createdBy: String,
    val resourceId: String,
    val timesheetDate: String,
    val workType: String,
    val workStatus: String,
    val blocker: String
)



data class ResourceDetails1(
    val FIRST_NAME: String,
    val LAST_NAME: String,
    val RESOURCE_NAME: String?,
    val EMAIL_ID: String,
    val RESOURCE_ID: String,
    val INTERNAL_LEAD_NAME: String,
    val PROJECT_NAME: String?,
    val CLIENT_LEAD_NAME: String
)
data class EmployeeResponse (
    val tableResponse: TableResponse
)

data class TableResponse (
    val counts: Counts,
    val trainees: List<Resource>?, // We can't know the type from the provided JSON so make it Any
    val employees: List<Resource>?,
    val consultants: List<Resource>?,
    val onboardingTracker: Any?
)



data class Counts (
    val Active: Active,
    val Archive: Archive
)

data class Active (
    val Employee: Int,
    val Trainee: Int,
    val Consultant: Int,
    val Management: Int,
    val OnboardingTracker:Int?
)

data class Archive (
    val Employee: Int,
    val Trainee: Int,
    val Consultant: Int
)

data class Resource (
    val FIRST_NAME: String,
    val RESOURCE_NAME: String?,
    val LAST_NAME: String,
    val RESOURCE_ID: String,
    val BILLING_STATUS: String?,
    val EMAIL_ID: String,
    val RESOURCE_TYPE: String?,
    val INTERNAL_LEAD_NAME: String?,
    val CURRENT_CLIENT_LEAD_NAME: String?,
    val ONBOARD_DATE: String?,
    val TECH_STACK_NAME: String?,
    val CLIENT_POSITION: String?,
    val CLIENT_NAME: String?,
    val COLLEGE_NAME: String?,
    val DEGREE: String?
)




//########################################################
data class EmployeeOrConsultantDetails(
    val firstName: String,
    val lastName: String,
    val resourceId: String,
    val infomericaOnboardedDate: String,
    val infomericaLastWorkingDate: String?,
    val visaValidityDate: String?,
    val resourceType: String,
    val billingStatus: String,
    val emailId: String,
    val roleName: String,
    val areaOfTechnology: String,
    val resourceCurrentPosition: String,
    val resourceCurrentLocation: String,
    val currentInfomericaLeadName: String,
    val visaStatus: String?,
    val visaType: String,
    val notes: String,
    val notes1: String?,
    val clientDetails: List<List<ClientDetail>>,
)

data class ClientDetail(
    val clientName: String,
    val clientPosition: String,
    val clientEmployeeId: Long,
    val clientWorkerId: String,
    val clientOnboardedDate: String,
    val clientBillingStartDate: String,
    val clientOffBoardedDate: String?,
    val resourceClientId: Int,
    val projectDetails: List<ProjectDetail>
)

data class ProjectDetail(
    val clientName: String,
    val projectName: String,
    val projectStartDate: String,
    val projectEndDate: String,
    val clientLeadName: String,
    val resourceClientId: Int,
    val resourceClientProjectMappingId: Int
)

data class LeadsProjectsHistory(
    val projectName: String,
    val startDate: String,
    val endDate: String,
    val infomericaLeadName: String,
    val currentClientLeadName: String?
)


data class DetailedViewApiResponse(
    val EmployeeOrConsultantDetails: EmployeeOrConsultantDetails,
    val leadsProjectsHistory: List<LeadsProjectsHistory>,
    val traineeDetails:TraineeDetails
)

//########################################################
data class TraineeDetails(
    val firstName: String?,
    val lastName: String?,
    val resourceId: String,
    val resourceType: String?,
    val emailId: String?,
    val infomericaOnboardedDate: String?,
    val infomericaLastWorkingDate: String?,
    val degreeAndSpecialization: String?,
    val passedOutYear: String?,
    val college: String?,
    val areaOfTechnology: String?,
    val experience: String?,
    val notes: String?
)


data class ResourceDetailsRequest(
    val firstName: String? = null,
    val lastName: String? = null,
    val resourceId: String? = null,
    val infomericaOnboardedDate: String? = null,
    val infomericaLastWorkingDate: String? = null,


    val resourceType: String? = null,

    val emailId: String? = null,
    val roleId: Int? = null,
    val techStackId: Int? = null,
    val designationId: Int? = null,

    val notes: String? = null,
    val degreeAndSpecialization: String? = null,
    val passedOutYear: String? = null,
    val college: String? = null,
    val experience: String? = null,
    val newResourceId: String? = null,
    val modifiedBy: String? = null

)

data class ResourceAdd(
    val resourceId: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val roleId: Int? = null,
    val techStackId: Int? = null,
    val internalLeadId: String? = null,
    val workStatus: String? = null,
    val resourceType: String? = null,
    val emailId: String? = null,
    val password: String? = null,
    val billingStatus: String? = null,
    val infomericaOnboardedDate: String? = null,
    val infomericaLastWorkingDate: String? = null,
    val archived: String? = null,
    val isTrainee: String? = null,
    val clientId: Int? = null,
    val currentLocationId: Int? = null,
    val currentDesignationId: Int? = null,
    val notes: String? = null,
    val clientOnboardedDate: String? = null,
    val clientBillingStartDate: String? = null,
    val clientLastWorkingDate: String? = null,
    val currentProject: String? = null,
    val clientEmployeeId: Int? = null,
    val clientPosition: String? = null,
    val passportNumber: String? = null,
    val visaType: String? = null,
    val visaStatus: String? = null,
    val visaValidityDate: String? = null,
    val clientWorkerId: String? = null,
    val currentClientLeadName: String? = null,
    val createdBy: String? = null,
    val modifiedBy: String? = null,
    val degreeSpecification: String? = null,
    val passedOutYear: String? = null,
    val collegeName: String? = null,
    val experience: String? = null,
    val projectStartDate: String? = null,
    val projectEndDate: String? = null
)

data class UpdateResourceDetailsRequest(
    val firstName: String,
    val lastName: String,
    val resourceId: String,
    val infomericaOnboardedDate: String,
    val infomericaLastWorkingDate: String,
    val visaValidityDate: String,
    val resourceType: String,
    val billingStatus: String,
    val emailId: String,
    val roleId: Int,
    val techStackId: Int,
    val designationId: Int,
    val locationId: Int,
    val internalLeadId: String,
    val visaStatus: String,
    val visaType: String,
    val notes: String,
    val notes1: String,
    val degreeAndSpecialization: String,
    val passedOutYear: String,
    val college: String,
    val experience: String,
    val passportNumber: String,
    val newResourceId: String,
    val modifiedBy: String,
    val clientDetails: List<UpdateClientDetailRequest>
)

data class UpdateClientDetailRequest(
    val resourceClientId: Int,
    val clientId: Int,
    val clientEmployeeId: Int,
    val clientWorkerId: String,
    val clientOnboardedDate: String,
    val clientBillingStartDate: String,
    val clientOffBoardedDate: String,
    val clientPosition: String,
    val projectDetails: List<UpdateProjectDetailRequest>
)

data class UpdateProjectDetailRequest(
    val resourceClientprojectMappingId: Int,
    val resourceClientId: Int,
    val clientId: Int,
    val clientLeadName: String,
    val projectName: String,
    val projectStartDate: String,
    val projectEndDate: String
)






