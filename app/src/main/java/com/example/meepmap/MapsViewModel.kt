package com.example.meepmap

import Resource
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MapsViewModel(application: Application) : AndroidViewModel(application) {
    private val resources: MutableLiveData<List<Resource>> by lazy {
        MutableLiveData<List<Resource>>().also {
            loadUsers()
        }
    }

    fun getUsers(): LiveData<List<Resource>> {
        return resources
    }

    private fun loadUsers() {
        // Do an asynchronous operation to fetch users.
    }
}