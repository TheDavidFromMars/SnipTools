package com.jaqxues.sniptools.packimpl

import com.jaqxues.sniptools.pack.KnownExternalDestinations
import com.jaqxues.sniptools.packimpl.fragment.Destinations
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 03.11.20 - Time 13:04.
 */
class DestinationTests {
    @Test
    fun `Assert that all Pack destinations are in KnownDestinations`() {
        val knownByRoute = KnownExternalDestinations.values().associateBy { it.route }
        assertTrue(
            "A destination in the Pack was not implemented into KnownDestinations in the Apk.",
            Destinations.values().all { it.destination.route in knownByRoute }
        )
    }
}