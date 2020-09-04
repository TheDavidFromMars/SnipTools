package com.jaqxues.sniptools.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jaqxues.sniptools.repository.PackRepository
import kotlin.random.Random


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 05.06.20 - Time 19:41.
 */
class PackViewModel(private val packRepo: PackRepository): ViewModel() {
    val lastChecked: LiveData<Long> = MutableLiveData(System.currentTimeMillis() - Random.nextLong(0, 1e8.toLong()))

    val localPacks: LiveData<List<String>> = MutableLiveData(listOf("10.41.6.0", "10.89.7.72", "10.23.62.24"))
    val serverPacks: LiveData<List<String>> = MutableLiveData(listOf("11.0.0.0", "11.23.0.3", "12.32.3.23"))
}