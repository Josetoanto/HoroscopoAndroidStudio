package com.josetoanto.horoscope.features.horoscope.di

import com.josetoanto.horoscope.features.horoscope.data.repository.HoroscopeRepositoryImpl
import com.josetoanto.horoscope.features.horoscope.domain.repository.HoroscopeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class HoroscopeModule {

    @Binds
    @Singleton
    abstract fun bindHoroscopeRepository(
        impl: HoroscopeRepositoryImpl
    ): HoroscopeRepository
}
