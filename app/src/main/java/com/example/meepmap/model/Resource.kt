import com.google.gson.annotations.SerializedName


data class Resource (

    @SerializedName("id") val id : String,
    @SerializedName("name") val name : String,
    @SerializedName("x") val x : Double,
    @SerializedName("y") val y : Double,
    @SerializedName("scheduledArrival") val scheduledArrival : Int,
    @SerializedName("locationType") val locationType : Int,
    @SerializedName("companyZoneId") val companyZoneId : Int,
    @SerializedName("lat") val lat : Double,
    @SerializedName("lon") val lon : Double
)