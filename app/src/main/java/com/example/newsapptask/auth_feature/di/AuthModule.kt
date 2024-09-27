package com.example.newsapptask.auth_feature.di

import android.content.Context
import androidx.room.Room
import com.example.newsapptask.auth_feature.data.datastore.LoginDataStore
import com.example.newsapptask.auth_feature.data.db.UserDao
import com.example.newsapptask.auth_feature.data.db.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun providesUserDatabase(@ApplicationContext context: Context): UserDatabase =
        Room.databaseBuilder(context, UserDatabase::class.java, "appDatabase")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun providesUserDao(mealDatabase: UserDatabase): UserDao =
        mealDatabase.userDao()
    @Provides
    @Singleton
    fun provideLoginDataStore(@ApplicationContext context: Context): LoginDataStore {
        return LoginDataStore(context)
    }
}