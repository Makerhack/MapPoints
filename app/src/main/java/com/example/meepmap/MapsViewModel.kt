package com.example.meepmap

import com.example.meepmap.model.Resource
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.meepmap.repository.ResourcesRepository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MapsViewModel(application: Application) : AndroidViewModel(application) {
    private var resources: LiveData<List<Resource>?>? = null
    private var resourceRespository: ResourcesRepository =
        ResourcesRepository.getInstance(application)

    fun loadResources(): LiveData<List<Resource>?>? {
        if (resources == null) {
            resources =
                resourceRespository.getResources(Constants.LOWER_LEFT, Constants.UPPER_RIGHT)
        }
        return resources
    }

    fun updateResources(lowerLeft: LatLng, upperRight: LatLng) {
        GlobalScope.launch { resourceRespository.updateValueFromNetwork(lowerLeft, upperRight) }
    }

}