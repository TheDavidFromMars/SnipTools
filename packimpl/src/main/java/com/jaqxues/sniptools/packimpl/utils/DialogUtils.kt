package com.jaqxues.sniptools.packimpl.utils

import android.app.AlertDialog
import com.jaqxues.sniptools.utils.ContextContainer
import timber.log.Timber


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 04.07.20 - Time 11:12.
 */
inline fun createDialog(config: AlertDialog.Builder.() -> Unit) =
    AlertDialog.Builder(
        ContextContainer.getActivity() ?: error("Activity must be not-null to show Dialog!")
    ).apply(config).create()
