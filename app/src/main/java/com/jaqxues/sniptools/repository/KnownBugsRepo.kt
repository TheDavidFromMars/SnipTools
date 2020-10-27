package com.jaqxues.sniptools.repository

import com.jaqxues.sniptools.db.CustomTypeConverters
import com.jaqxues.sniptools.db.KnownBugDao
import com.jaqxues.sniptools.db.KnownBugEntity
import com.jaqxues.sniptools.networking.GitHubApiService


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnapTools.<br>
 * Date: 17.10.20 - Time 19:20.
 */
class KnownBugsRepo(private val api: GitHubApiService, private val bugDao: KnownBugDao) {
    fun getBugsFor(scVersion: String, packVersion: String) =
        bugDao.getBugsFor(scVersion, packVersion)

    suspend fun refreshBugsFor(scVersion: String, packVersion: String) {
        try {
            api.getKnownBugsFor(scVersion)[packVersion]?.let { bugs ->
                val c = CustomTypeConverters()
                bugDao.updateBugs(scVersion, packVersion, *bugs.map {
                    KnownBugEntity(
                        it.category, it.description,
                        filedOn = c.fromTimestamp(it.filedOn)!!,
                        fixedOn = c.fromTimestamp(it.fixedOn),
                        id = -1
                    )
                }.toTypedArray())
            }
        } catch (ignored: Throwable) {}
    }
}