package com.emmanuel.pastor.simplesmartapps.tricycle

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emmanuel.pastor.simplesmartapps.tricycle.platform.BleClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val bleClient: BleClient) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val connectionState: StateFlow<ConnectionState> = bleClient.isConnectedStateFlow.combine(_isLoading) { isConnected, isLoading ->
        ConnectionState(isConnected, isLoading)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ConnectionState(isConnected = false, isLoading = false)
    )

    fun onReconnectClicked() {
        viewModelScope.launch {
            _isLoading.value = true
            bleClient.openConnection("AAAAAAAAAA").onFailure {
                Log.e("MainViewModel", "${it.message}")
            }
            _isLoading.value = false
        }
    }
}

data class ConnectionState(val isConnected: Boolean, val isLoading: Boolean)