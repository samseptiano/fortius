package com.samseptiano.base.data

import com.google.gson.annotations.SerializedName

class ResponseWrapper<T>(
    @field:SerializedName("meta")
    var meta: Meta? = null,
    @field:SerializedName("results")
    var results: T,
)

data class Meta(
    @field:SerializedName("status")
    var status: Int? = 0,
    @field:SerializedName("result")
    var result: Boolean? = true,
    @field:SerializedName("message")
    var message: String? = "",
)