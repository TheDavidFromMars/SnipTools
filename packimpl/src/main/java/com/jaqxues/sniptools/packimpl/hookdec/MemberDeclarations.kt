package com.jaqxues.sniptools.packimpl.hookdec

import com.jaqxues.akrolyb.genhook.decs.MemberDec.ConstructorDec
import com.jaqxues.akrolyb.genhook.decs.MemberDec.MethodDec
import com.jaqxues.sniptools.packimpl.ScreenshotBypass
import com.jaqxues.sniptools.packimpl.StealthViewing
import com.jaqxues.sniptools.packimpl.UnlimitedViewing
import com.jaqxues.sniptools.packimpl.hookdec.ClassDeclarations.SCREENSHOT_DETECTOR
import com.jaqxues.sniptools.packimpl.hookdec.ClassDeclarations.SNAP_MODEL
import com.jaqxues.sniptools.packimpl.hookdec.ClassDeclarations.STORY_VIEWED_PLUGIN

object MemberDeclarations {
    val SCREENSHOT_DETECTED = MethodDec(
        SCREENSHOT_DETECTOR,
        "a",
        arrayOf(ScreenshotBypass::class.java),

        SCREENSHOT_DETECTOR, "qD5"
    )

    val MARK_STORY_AS_VIEWED = MethodDec(
        STORY_VIEWED_PLUGIN,
        "j0",
        arrayOf(StealthViewing::class.java),

        "SGd"
    )

    val SNAP_MODEL_CONSTRUCTOR = ConstructorDec(
        SNAP_MODEL,
        arrayOf(UnlimitedViewing::class.java),

        String::class.java, Boolean::class.java, String::class.java, String::class.java,
        String::class.java, Long::class.javaObjectType, "Ds5", Long::class.java,
        Boolean::class.java, Long::class.javaObjectType, Long::class.java
    )
}