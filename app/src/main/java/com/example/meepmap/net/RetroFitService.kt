package com.example.meepmap.net

import com.google.android.gms.maps.model.LatLng
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type


object RetrofitService {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://apidev.meep.me/tripplan/api/v1/routers/lisboa/")
        .addConverterFactory(GsonConverterFactory.create())
        .addConverterFactory(object : Converter.Factory() {
            override fun stringConverter(
                type: Type,
                annotations: Array<Annotation>,
                retrofit: Retrofit
            ): Converter<*, String>? {
                if (type == LatLng::class.java) {
                    return Converter<LatLng, String> { it.latitude.toString() + "," + it.longitude.toString() }
                }
                return super.stringConverter(type, annotations, retrofit)
            }
        })
        .build()

    fun <S> createService(serviceClass: Class<S>): S {
        return retrofit.create(serviceClass)
    }
}