package com.jaqxues.sniptools.utils

import com.jaqxues.sniptools.pack.PackMetadata
import java.io.File
import java.util.jar.Attributes


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 05.06.20 - Time 13:04.
 */
fun Attributes.buildMetadata(file: File): PackMetadata {
    fun getOrThrow(name: String) =
        getValue(name) ?: throw IllegalStateException("Pack did not include \"$name\" Attribute")

    return PackMetadata(
        flavour = getOrThrow("Flavor"),
        scVersion = getOrThrow("ScVersion"),
        name = file.name.dropLast(4),
        devPack = getOrThrow("Development").equals("true", false),
        packVersion = getOrThrow("PackVersion"),
        packVersionCode = getOrThrow("PackVersionCode").toInt(),
        packImplClass = getOrThrow("PackImplClass"),
        minApkVersionCode = getOrThrow("MinApkVersionCode").toInt(),
        isEncrypted = getValue("Encrypted")?.toBoolean() == true
    )
}