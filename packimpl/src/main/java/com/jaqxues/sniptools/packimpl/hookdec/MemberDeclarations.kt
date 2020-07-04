package com.jaqxues.sniptools.packimpl.hookdec

import com.jaqxues.akrolyb.genhook.decs.MemberDec
import com.jaqxues.sniptools.packimpl.ScreenshotBypass
import com.jaqxues.sniptools.packimpl.hookdec.ClassDeclarations.SCREENSHOT_DETECTOR

object MemberDeclarations {
    val SCREENSHOT_DETECTED = MemberDec.MethodDec(
        SCREENSHOT_DETECTOR,
        "a",
        arrayOf(ScreenshotBypass::class.java),
        SCREENSHOT_DETECTOR, "qD5"
    )
}