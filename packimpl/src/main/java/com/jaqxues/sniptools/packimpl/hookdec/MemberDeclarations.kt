package com.jaqxues.sniptools.packimpl.hookdec

import com.jaqxues.akrolyb.genhook.decs.MemberDec.*
import com.jaqxues.sniptools.packimpl.ScreenshotBypass
import com.jaqxues.sniptools.packimpl.StealthViewing
import com.jaqxues.sniptools.packimpl.hookdec.ClassDeclarations.SCREENSHOT_DETECTOR
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
}