package com.example.androidconcepts.jcip.extras

import android.util.Log
import com.example.androidconcepts.jcip.common.ThreadContextSwitchTrigger

/**
 * creates many threads to test for OOM errors
 */
class AndroidThreadSpammer {

    fun execute() {
        val contextSwitchTrigger = ThreadContextSwitchTrigger(5000L)

        Thread {
            repeat(500) {
                Thread {
                    Log.d("thread exp", "number : $it")
                    contextSwitchTrigger.trigger()
                }.start()
            }
        }.start()
    }
}