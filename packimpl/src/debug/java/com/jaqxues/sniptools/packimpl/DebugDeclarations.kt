package com.jaqxues.sniptools.packimpl

import com.jaqxues.akrolyb.genhook.decs.ClassDec
import com.jaqxues.akrolyb.genhook.decs.MemberDec
import com.jaqxues.sniptools.packimpl.DebugClassDeclarations.CONTENT_PROVIDER


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 04.07.20 - Time 17:24.
 */
object DebugClassDeclarations {
    val CONTENT_PROVIDER = ClassDec(
        "com.snap.media.provider.MediaPackageFileProvider"
    )
}

object DebugMemberDeclarations {
    val ON_CREATE_PROVIDER = MemberDec.MethodDec(
        CONTENT_PROVIDER,
        "onCreate",
        arrayOf(DebugFeature::class.java)
    )
}