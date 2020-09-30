package com.jaqxues.sniptools.packimpl.features

import android.content.Context
import com.jaqxues.akrolyb.genhook.decs.after
import com.jaqxues.sniptools.fragments.BaseFragment
import com.jaqxues.sniptools.pack.IFeature
import com.jaqxues.sniptools.packimpl.hookdec.MemberDeclarations.DECRYPT_MEDIA_STREAM
import java.io.ByteArrayInputStream
import java.io.File
import java.io.InputStream


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 24.08.20 - Time 13:12.
 */
class SavingFeature : IFeature() {
    override fun getFragments(): Array<BaseFragment> {
        TODO("Not yet implemented")
    }

    override val name: Int
        get() = TODO("Not yet implemented")

    override fun loadFeature(classLoader: ClassLoader, context: Context) {
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
