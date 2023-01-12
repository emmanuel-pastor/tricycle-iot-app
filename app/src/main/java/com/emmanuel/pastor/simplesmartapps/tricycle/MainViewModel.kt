package com.emmanuel.pastor.simplesmartapps.tricycle

import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.emmanuel.pastor.simplesmartapps.tricycle.platform.BleClient
import com.emmanuel.pastor.simplesmartapps.tricycle.services.TricycleService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val bleClient: BleClient, application: Application) : AndroidViewModel(application) {
    companion object {
        private const val TAG = "MainViewModel"
        private const val TRICYCLE_SERIAL_NUMBER = "7836082"
    }

    private val _isLoading = MutableStateFlow(false)
    val connectionState: StateFlow<ConnectionState> = bleClient.isConnectedStateFlow.combine(_isLoading) { isConnected, isLoading ->
        ConnectionState(isConnected, isLoading)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ConnectionState(isConnected = false, isLoading = false)
    )

    /**
     * Requires Bluetooth permissions
     *
     * Requires Notification permission on [android.os.Build.VERSION_CODES.TIRAMISU] and above
     */
    fun onReconnectClicked() {
        viewModelScope.launch {
            _isLoading.value = true
            bleClient.openConnection(TRICYCLE_SERIAL_NUMBER).onSuccess {
                with(getApplication() as Context) {
                    startForegroundService(Intent(this, TricycleService::class.java))
                }
            }.onFailure {
                Log.e(TAG, "${it.message}")
            }
        }.invokeOnCompletion { _isLoading.value = false }
    }
}

data class ConnectionState(val isConnected: Boolean, val isLoading: Boolean)