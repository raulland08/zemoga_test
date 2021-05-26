package com.zemoga

import android.app.Application
import com.zemoga.framework.di.dataModule
import com.zemoga.framework.di.usersModule
import io.realm.Realm
import io.realm.RealmConfiguration
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class ZemogaApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initDependencyInjection()
        initRealm()
    }

    /**
     * Initialize Koin
     */
    private fun initDependencyInjection() {
        startKoin {
            androidContext(this@ZemogaApplication)
            modules(
                listOf(
                    dataModule,
                    usersModule
                )
            )
        }
    }

    private fun initRealm() {
        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .name("zemoga_db.realm")
            .deleteRealmIfMigrationNeeded()
            .schemaVersion(1)
            .allowWritesOnUiThread(true)
            .build()
        Realm.setDefaultConfiguration(config)
    }
}