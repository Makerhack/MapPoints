package com.example.meepmap.repository
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.meepmap.database.AppDatabase
import com.example.meepmap.model.Resource
import com.example.meepmap.model.ResourceResponse
import com.example.meepmap.net.ResourcesApi
import com.example.meepmap.net.RetrofitService
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResourcesRepository(context: Context) {
    private val resourcesApi: ResourcesApi = RetrofitService.createService(ResourcesApi::class.java)
    private var resourceData: LiveData<List<Resource>?> = MutableLiveData()
    private val userDao = AppDatabase.getInstance(context).userDao()

    init {
        resourceData = userDao.getAll()
    }

    fun getResources(
        lowerLeftLatLon: LatLng,
        upperRightLatLon: LatLng
    ): LiveData<List<Resource>?> {
        GlobalScope.launch { updateValueFromNetwork(lowerLeftLatLon, upperRightLatLon) }
        return resourceData
    }

    suspend fun updateValueFromNetwork(
        lowerLeftLatLon: LatLng,
        upperRightLatLon: LatLng
    ) = withContext(Dispatchers.IO) {
        Log.d(TAG, "Updating value from Network")
        resourcesApi.getResourcesList(lowerLeftLatLon, upperRightLatLon)
            .enqueue(object : Callback<List<Resource>?> {
                override fun onResponse(
                    call: Call<List<Resource>?>?,
                    response: Response<List<Resource>?>?
                ) {
                    Log.d(TAG, "Fetch response: {$response")
                    if (response?.isSuccessful == true) {
                        val resources = response.body()
                        resources?.let {
                            GlobalScope.launch { writeDb(it) }
                        }
                    }
                }

                override fun onFailure(call: Call<List<Resource>?>?, t: Throwable?) {
                    Log.e(TAG, "Failture getting response")
                    t?.printStackTrace()
                }
            })
    }

    suspend fun writeDb(resources: List<Resource>) = withContext(Dispatchers.IO){
        userDao.insertAll(resources)
    }

    companion object {
        const val TAG = "ResourceRepository"
        private var INSTANCE: ResourcesRepository? = null
        fun getInstance(context: Context): ResourcesRepository {
            if (INSTANCE == null) {
                INSTANCE = ResourcesRepository(context)
            }
            return INSTANCE as ResourcesRepository
        }
    }


}