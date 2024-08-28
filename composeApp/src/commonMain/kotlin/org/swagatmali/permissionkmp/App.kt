package org.swagatmali.permissionkmp

import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.ui.Modifier
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.icerock.moko.permissions.PermissionState
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        val factory = rememberPermissionsControllerFactory()
        val controller = remember(factory) {
            factory.createPermissionsController()
        }

        BindEffect(controller)
        val viewmodel = viewModel {
            PermissionsViewModel(controller)
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (viewmodel.state) {
                PermissionState.Granted -> Text(
                    modifier = Modifier,
                    text = "Record Audio Permission Granted",
                    style = MaterialTheme.typography.h2,
                    textAlign = TextAlign.Start,
                )

                PermissionState.DeniedAlways -> {
                    Text(
                        modifier = Modifier,
                        text = "Permission was permanently denied",
                        style = MaterialTheme.typography.h2,
                        textAlign = TextAlign.Start
                    )
                    Button(onClick = {
                        controller.openAppSettings()
                    }) {
                        Text(text = "Open Settings")
                    }
                }

                else -> Button(onClick = {
                    viewmodel.provideOrRequestRecordAudioPermissions()
                }) {
                    Text(text = "Request Remission")
                }
            }
        }
    }
}