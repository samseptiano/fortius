package com.samseptiano.fortius.data.model.response

import com.google.gson.annotations.SerializedName

data class MapResponse(

	@field:SerializedName("continent")
	val continent: String? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("latitude")
	val latitude: Any? = null,

	@field:SerializedName("plusCode")
	val plusCode: String? = null,

	@field:SerializedName("locality")
	val locality: String? = null,

	@field:SerializedName("postcode")
	val postcode: String? = null,

	@field:SerializedName("localityInfo")
	val localityInfo: LocalityInfo? = null,

	@field:SerializedName("principalSubdivisionCode")
	val principalSubdivisionCode: String? = null,

	@field:SerializedName("principalSubdivision")
	val principalSubdivision: String? = null,

	@field:SerializedName("lookupSource")
	val lookupSource: String? = null,

	@field:SerializedName("countryCode")
	val countryCode: String? = null,

	@field:SerializedName("countryName")
	val countryName: String? = null,

	@field:SerializedName("longitude")
	val longitude: Any? = null,

	@field:SerializedName("localityLanguageRequested")
	val localityLanguageRequested: String? = null,

	@field:SerializedName("continentCode")
	val continentCode: String? = null
)

data class InformativeItem(

	@field:SerializedName("geonameId")
	val geonameId: Int? = null,

	@field:SerializedName("wikidataId")
	val wikidataId: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("order")
	val order: Int? = null,

	@field:SerializedName("isoCode")
	val isoCode: String? = null,

	@field:SerializedName("isoName")
	val isoName: String? = null
)

data class LocalityInfo(

	@field:SerializedName("administrative")
	val administrative: List<AdministrativeItem?>? = null,

	@field:SerializedName("informative")
	val informative: List<InformativeItem?>? = null
)

data class AdministrativeItem(

	@field:SerializedName("adminLevel")
	val adminLevel: Int? = null,

	@field:SerializedName("geonameId")
	val geonameId: Int? = null,

	@field:SerializedName("wikidataId")
	val wikidataId: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("order")
	val order: Int? = null,

	@field:SerializedName("isoCode")
	val isoCode: String? = null,

	@field:SerializedName("isoName")
	val isoName: String? = null
)
