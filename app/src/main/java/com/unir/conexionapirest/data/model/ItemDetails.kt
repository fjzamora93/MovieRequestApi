package com.unir.conexionapirest.data.model


import com.google.gson.annotations.SerializedName


data class ItemDetails(
    @SerializedName("_id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("price")
    val price: Double,

    @SerializedName("category")
    val category: String,

    @SerializedName("image")
    val image: String,

    @SerializedName("active")
    val active: Boolean
)

