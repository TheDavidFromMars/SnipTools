package com.jaqxues.sniptools.repository

import com.jaqxues.sniptools.db.KnownBugDao
import com.jaqxues.sniptools.networking.GitHubApiService


/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnapTools.<br>
 * Date: 17.10.20 - Time 19:20.
 */
class KnownBugsRepo(private val api: GitHubApiService, private val bugDao: KnownBugDao) {

}