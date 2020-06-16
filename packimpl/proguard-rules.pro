# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Keep the PackImpl Class (instantiated by reflection)
-keep public class com.jaqxues.sniptools.packimpl.PackImpl
# Keep everything in the PackImpl Class (but allow obfuscation and optimization). These are all the entry points of a
# Pack and hence keeping all the Members and Fields of this class makes it safe to allow minifying the pack.
-keep ,allowobfuscation, allowoptimization, public class com.jaqxues.sniptools.packimpl.PackImpl { *; }
