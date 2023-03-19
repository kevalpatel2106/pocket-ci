# Uncomment this to preserve the line number information for
# debugging stack traces.
-keepattributes SourceFile,LineNumberTable

# Auto generated missing rules
-dontwarn java.lang.invoke.StringConcatFactory

# Setting the build versions for dead code elemination.
# https://jakewharton.com/digging-into-d8-and-r8/
-assumevalues class android.os.Build$VERSION {
    int SDK_INT return 23..2147483647;
}

# Proguard configuration for removing logs
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
    public static *** w(...);
    public static *** e(...);
}
-assumenosideeffects class java.io.PrintStream {
     public void println(%);
     public void println(**);
 }

# sqlcipher: https://github.com/sqlcipher/android-database-sqlcipher#proguard
-keep,includedescriptorclasses class net.sqlcipher.** { *; }
-keep,includedescriptorclasses interface net.sqlcipher.** { *; }
# End of sqlcipher

# Navigation
# We need to prevent Parcelable, Serializable, and Enum class names from being obfuscated if they
# are used by SafeArs plugin.
# Source: https://developer.android.com/guide/navigation/navigation-pass-data#proguard_considerations
-keepnames class com.kevalpatel2106.entity.**
# End of navigation
