package com.jaqxues.sniptools.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaqxues.sniptools.db.KnownBugEntity
import com.jaqxues.sniptools.repository.KnownBugsRepo
import kotlinx.coroutines.launch


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnapTools.<br>
 * Date: 27.10.20 - Time 19:20.
 */
class KnownBugsViewModel @ViewModelInject constructor(private val repository: KnownBugsRepo): ViewModel() {
    fun getBugsFor(scVersion: String, packVersion: String): LiveData<List<KnownBugEntity>> {
        viewModelScope.launch {
            repository.refreshBugsFor(scVersion, packVersion)
        }
        return repository.getBugsFor(scVersion, packVersion)
    }
}