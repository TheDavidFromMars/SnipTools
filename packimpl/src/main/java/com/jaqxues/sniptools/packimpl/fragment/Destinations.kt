package com.jaqxues.sniptools.packimpl.fragment

import com.jaqxues.sniptools.pack.ExternalDestination

/**
 * This file was created by Jacques Hoffmann (jaqxues) in the Project SnipTools.<br>
 * Date: 03.11.20 - Time 12:33.
 */
enum class Destinations(val destination: ExternalDestination) {
    GENERAL(
        ExternalDestination("general", "General") { GeneralScreen() }
    ),
    MISC(
        ExternalDestination("misc", "Misc Changes") { MiscChangesScreen() }
    ),
    SAVING(
        ExternalDestination("saving", "Saving") { SavingScreen() }
    ),
    SCREENSHOT(
        ExternalDestination("screenshot", "Screenshot Bypass") { ScreenshotScreen() }
    ),
    STEAlTH(
        ExternalDestination("stealth", "Stealth Viewing") { StealthScreen() }
    ),
    UNLIMITED(
        ExternalDestination("unlimited", "Unlimited Viewing") { UnlimitedScreen() }
    );
}