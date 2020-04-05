package com.example.meepmap.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


@Entity
data class Resource (
    @PrimaryKey @SerializedName("id") @Expose val id : String,
    @SerializedName("name") @Expose val name : String,
    @SerializedName("x") @Expose val x : Double,
    @SerializedName("y") @Expose val y : Double,
    @SerializedName("scheduledArrival") @Expose val scheduledArrival : Int,
    @SerializedName("locationType") @Expose val locationType : Int,
    @SerializedName("companyZoneId") @Expose val companyZoneId : Int,
    @SerializedName("lat") @Expose val lat : Double,
    @SerializedName("lon") @Expose val lon : Double
)