package com.jaqxues.sniptools.data

import com.jaqxues.akrolyb.pack.IPackMetadata


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnapTools.<br>
 * Date: 27.05.20 - Time 08:54.
 * Moved to SnipTools on Date: 03.06.20 - Time 17:45.
 */
interface PackMetadata : IPackMetadata {
    val isTutorial: Boolean
    val latest: Boolean
    val flavour: String
    val scVersion: String
    val name: String
}

data class ServerPackMetadata(
    val description: String,
    val isInstalled: Boolean,
    val hasUpdate: Boolean,

    override val isTutorial: Boolean,
    override val latest: Boolean,
    override val flavour: String,
    override val scVersion: String,
    override val name: String,

    override val devPack: Boolean,
    override val packVersion: String,
    override val packVersionCode: Int,
    override val packImplClass: String,
    override val minApkVersionCode: Int
) : PackMetadata

data class LocalPackMetadata(
    override val isTutorial: Boolean,
    override val latest: Boolean,
    override val flavour: String,
    override val scVersion: String,
    override val name: String,

    override val devPack: Boolean,
    override val packVersion: String,
    override val packVersionCode: Int,
    override val packImplClass: String,
    override val minApkVersionCode: Int
) : PackMetadata

data class ActivePackMetadata(
    val flavour: String,
    val scVersion: String,
    val name: String,

    override val devPack: Boolean,
    override val packVersion: String,
    override val packVersionCode: Int,
    override val packImplClass: String,
    override val minApkVersionCode: Int
): IPackMetadata