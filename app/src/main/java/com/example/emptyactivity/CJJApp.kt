package com.example.emptyactivity

import android.app.Application
import com.example.emptyactivity.data.AppModule

class CJJApp: Application() {
    /* Always be able to access the module ("static") */
    companion object {
        lateinit var appModule: AppModule
    }

    /* Called only once at beginning of application's lifetime */
    override fun onCreate() {
        super.onCreate()
        appModule = AppModule(this)
    }
}