package com.example.meepmap.net

import com.example.meepmap.model.Resource
import com.example.meepmap.model.ResourceResponse
import com.google.android.gms.maps.model.LatLng
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * https://apidev.meep.me/tripplan/api/v1/routers/lisboa/resources?lowerLeftLatLon=38.711046,-9.160096&upperRightLatLon=38.739429,-9.137115
 */

interface ResourcesApi {

    @GET("resources")
    fun getResourcesList(
        @Query("lowerLeftLatLon") lowerLeftLatLon: LatLng,
        @Query("upperRightLatLon") upperRightLatLon: LatLng
    ): Call<List<Resource>?>
}