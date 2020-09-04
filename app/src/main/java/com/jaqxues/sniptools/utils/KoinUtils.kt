package com.jaqxues.sniptools.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ViewModelStoreOwnerAmbient
import androidx.lifecycle.ViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 04.09.20 - Time 16:22.
 */
@Composable
inline fun <reified T: ViewModel> viewModel() = ViewModelStoreOwnerAmbient.current.viewModel<T>()