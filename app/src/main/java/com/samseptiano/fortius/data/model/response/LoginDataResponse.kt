package com.samseptiano.fortius.data.model.response

import com.google.gson.annotations.SerializedName

data class LoginDataResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("error")
	val error: String? = null
)

data class Company(

	@field:SerializedName("company_name")
	val companyName: String? = null,

	@field:SerializedName("logo")
	val logo: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("media")
	val media: List<Any?>? = null
)

data class User(

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("company_id")
	val companyId: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("company")
	val company: Company? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("employee")
	val employee: Employee? = null,

	@field:SerializedName("email")
	val email: String? = null
)

data class Data(

	@field:SerializedName("expires_at")
	val expiresAt: String? = null,

	@field:SerializedName("token_type")
	val tokenType: String? = null,

	@field:SerializedName("user")
	val user: User? = null,

	@field:SerializedName("token")
	val token: String? = null
)

data class Employee(

	@field:SerializedName("employee_img")
	val employeeImg: String? = null,

	@field:SerializedName("full_name")
	val fullName: String? = null,

	@field:SerializedName("user_id")
	val userId: String? = null,

	@field:SerializedName("employee_id")
	val employeeId: String? = null,

	@field:SerializedName("employee_last_name")
	val employeeLastName: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("employee_first_name")
	val employeeFirstName: String? = null,

	@field:SerializedName("employee_department")
	val employeeDepartment: String? = null
)
