package com.jaqxues.sniptools.packimpl

import com.jaqxues.akrolyb.graphguard.GenerateGraphGuardDecs
import com.jaqxues.sniptools.packimpl.hookdec.ClassDeclarations
import com.jaqxues.sniptools.packimpl.hookdec.MemberDeclarations

/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 12.08.20 - Time 16:29.
 */
fun main() {
    GenerateGraphGuardDecs.printGeneratedDecs(
        ClassDeclarations, MemberDeclarations, object {}
    )
}