package com.example.testcompose.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.testcompose.database.NoteDatabase
import com.example.testcompose.repositories.NoteRepository
import com.example.testcompose.util.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(appContext:Application): NoteDatabase{
       return Room.databaseBuilder(
                appContext,
                NoteDatabase::class.java,
                DATABASE_NAME
            ).build()
    }

    @Provides
    @Singleton
    fun provideDatabaseRepository(database: NoteDatabase) = NoteRepository(database)

}