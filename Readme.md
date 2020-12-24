# SnipTools

SnipTools uses the MVVM pattern, together with the latest Architecture Components such as the Room
Database Library. It also uses Jetpack Compose, a declarative UI Framework that is sill in alpha.
Hence SnipTools needs to be compiled with the ***latest Android Studio Canary***.

## Build Instructions and Gradle Tasks

SnipTools uses a [Gradle Plugin](https://github.com/jaqxues/PackCompiler) to compile ModulePacks.
It also comes with a number of custom gradle tasks to automate the most part of updating Packs.
Have a look at the PackCompiler itself for understanding what the "default" tasks can achieve.
SnipTools itself does not only support to `adb push` the Packs automatically, but it comes 
preconfigured with tasks to restart applications in order to fully apply the changes in the Packs.
This automation is handled in 
[packimpl/build.gradle](https://github.com/jaqxues/SnipTools/blob/master/packimpl/build.gradle).

***Notice***: The compile process will fail at first attempt in order to let you know that there are
configuration options in an auto-generated file called `Secrets/AdbPushConfig.json`. Read that file
carefully and configure it for your needs.

#### Important Tasks:
* `packimpl:applyPackChangesDebug` - Compiles, pushes, activates the Pack and restarts Snapchat
* `packimpl:openPackChangesDebug` - Compiles, pushes, activates the Pack and restarts SnipTools


Use the typical `app:installDebug` task to install the application via Gradle.
