package org.swagatmali.permissionkmp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission.RECORD_AUDIO
import dev.icerock.moko.permissions.PermissionState
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.RequestCanceledException
import kotlinx.coroutines.launch

class PermissionsViewModel(
    private val permissionsController: PermissionsController
) : ViewModel() {

    var state by mutableStateOf(PermissionState.NotDetermined)
        private set

    init {
        viewModelScope.launch {
            state = permissionsController.getPermissionState(RECORD_AUDIO)
        }
    }

    fun provideOrRequestRecordAudioPermissions() {

        viewModelScope.launch {
            try {
                permissionsController.providePermission(RECORD_AUDIO)
                state = PermissionState.Granted
            } catch (e: DeniedAlwaysException) {
                state = PermissionState.DeniedAlways
            } catch (e: DeniedException) {
                state = PermissionState.Denied
            } catch (e: RequestCanceledException) {
                e.printStackTrace()
            }
        }
    }
}