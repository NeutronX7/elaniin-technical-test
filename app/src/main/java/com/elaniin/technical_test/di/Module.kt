package com.elaniin.technical_test.di

import android.content.Context
import com.elaniin.technical_test.utils.Prefs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Singleton
    @Provides
    fun providePrefs(@ApplicationContext appContext: Context): Prefs {
        return Prefs(appContext)
    }

}