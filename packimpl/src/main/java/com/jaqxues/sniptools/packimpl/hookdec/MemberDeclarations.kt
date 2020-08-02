package com.jaqxues.sniptools.packimpl.hookdec

import com.jaqxues.akrolyb.genhook.decs.MemberDec.ConstructorDec
import com.jaqxues.akrolyb.genhook.decs.MemberDec.MethodDec
import com.jaqxues.sniptools.packimpl.MiscFeatures
import com.jaqxues.sniptools.packimpl.ScreenshotBypass
import com.jaqxues.sniptools.packimpl.StealthViewing
import com.jaqxues.sniptools.packimpl.UnlimitedViewing
import com.jaqxues.sniptools.packimpl.hookdec.ClassDeclarations.APP_START_EXPERIMENT_MANAGER
import com.jaqxues.sniptools.packimpl.hookdec.ClassDeclarations.SCREENSHOT_DETECTOR
import com.jaqxues.sniptools.packimpl.hookdec.ClassDeclarations.SNAP_MODEL
import com.jaqxues.sniptools.packimpl.hookdec.ClassDeclarations.STORY_VIEWED_PLUGIN

object MemberDeclarations {
    val SCREENSHOT_DETECTED = MethodDec(
        SCREENSHOT_DETECTOR,
        "a",
        arrayOf(ScreenshotBypass::class.java),

        SCREENSHOT_DETECTOR, "dG5"
    )

    val MARK_STORY_AS_VIEWED = MethodDec(
        STORY_VIEWED_PLUGIN,
        "j0",
        arrayOf(StealthViewing::class.java),

        "KTd"
    )

    val SNAP_MODEL_CONSTRUCTOR = ConstructorDec(
        SNAP_MODEL,
        arrayOf(UnlimitedViewing::class.java),

        String::class.java, Boolean::class.java, String::class.java, String::class.java,
        String::class.java, Long::class.javaObjectType, "jv5", Long::class.java,
        Boolean::class.java, Long::class.javaObjectType, Long::class.java
    )

    val FORCE_APP_DECK = MethodDec(
        APP_START_EXPERIMENT_MANAGER,
        "g",
        arrayOf(MiscFeatures::class.java),

        "iY4"
    )
}