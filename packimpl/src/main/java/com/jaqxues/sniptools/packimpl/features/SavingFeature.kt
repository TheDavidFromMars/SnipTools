package com.jaqxues.sniptools.packimpl.features

import android.content.Context
import com.jaqxues.akrolyb.genhook.decs.after
import com.jaqxues.akrolyb.prefs.getPref
import com.jaqxues.sniptools.pack.IFeature
import com.jaqxues.sniptools.packimpl.fragment.SavingFragment
import com.jaqxues.sniptools.packimpl.hookdec.MemberDeclarations.DECRYPT_MEDIA_STREAM
import com.jaqxues.sniptools.packimpl.utils.PackPreferences.AUTO_SAVE_SNAPS
import java.io.ByteArrayInputStream
import java.io.File
import java.io.InputStream


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 24.08.20 - Time 13:12.
 */
class SavingFeature : IFeature() {
    override fun getFragments() = arrayOf(SavingFragment())

    override fun loadFeature(classLoader: ClassLoader, context: Context) {
        if (AUTO_SAVE_SNAPS.getPref())
            hookMethod(DECRYPT_MEDIA_STREAM, after {
                val result = it.result as InputStream
                val bytes = result.use(InputStream::readBytes)

                val folder = File("/storage/emulated/0/SnipTools/Test").apply {
                    if (!exists()) mkdirs()
                }

                File(folder, "Auto_Save_${System.currentTimeMillis()}_${System.nanoTime()}.jpg").apply {
                    createNewFile()
                    writeBytes(bytes)
                }

                it.result = ByteArrayInputStream(bytes)
            })
    }
}
