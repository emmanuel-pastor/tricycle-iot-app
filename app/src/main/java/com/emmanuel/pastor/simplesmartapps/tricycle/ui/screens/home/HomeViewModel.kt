package com.emmanuel.pastor.simplesmartapps.tricycle.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emmanuel.pastor.simplesmartapps.tricycle.domain.TricycleRepo
import com.emmanuel.pastor.simplesmartapps.tricycle.domain.models.TricycleData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(private val tricycleRepo: TricycleRepo) : ViewModel() {
    init {
        refreshTricycleData()
    }

    val sensorSectionState: StateFlow<SensorSectionState> = tricycleRepo.getTricycleData().map {
        flowOf(SensorSectionState.Success(it))
    }.flatMapLatest { it }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = SensorSectionState.Loading
    )

    private fun refreshTricycleData() = viewModelScope.launch {
        while (true) {
            tricycleRepo.refreshTricycleData()
            delay(10_000)
        }
    }
}

sealed interface SensorSectionState {
    object Loading : SensorSectionState
    data class Success(
        val tricycleData: TricycleData
    ) : SensorSectionState
}