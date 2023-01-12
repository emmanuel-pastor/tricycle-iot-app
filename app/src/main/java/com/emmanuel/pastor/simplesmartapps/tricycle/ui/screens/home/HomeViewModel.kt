package com.emmanuel.pastor.simplesmartapps.tricycle.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emmanuel.pastor.simplesmartapps.tricycle.domain.TricycleRepo
import com.emmanuel.pastor.simplesmartapps.tricycle.domain.models.TricycleData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val tricycleRepo: TricycleRepo) : ViewModel() {
    val sensorSectionState: StateFlow<SensorSectionState> = tricycleRepo.fetchTricycleData().map {
        SensorSectionState.Success(it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = SensorSectionState.Loading
    )
}

sealed interface SensorSectionState {
    object Loading : SensorSectionState
    data class Success(
        val tricycleData: TricycleData
    ) : SensorSectionState
}