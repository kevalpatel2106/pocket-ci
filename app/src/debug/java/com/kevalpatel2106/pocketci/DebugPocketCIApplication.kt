package com.kevalpatel2106.pocketci

import android.content.Context
import com.bytedance.android.bytehook.ByteHook
import dagger.hilt.android.HiltAndroidApp
import wtf.s1.android.thread.bhook.S1ThreadHooker

@HiltAndroidApp
class DebugPocketCIApplication : PocketCIApplication() {
    override fun attachBaseContext(base: Context?) {
        attachFlipperThreadInspector()
        super.attachBaseContext(base)
    }

    private fun attachFlipperThreadInspector() {
        ByteHook.init(
            ByteHook.ConfigBuilder()
                .setMode(ByteHook.Mode.AUTOMATIC)
                .setDebug(BuildConfig.DEBUG)
                .build(),
        )
        S1ThreadHooker.hookThread()
    }
}
