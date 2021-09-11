package com.example.flashcard.di

import android.content.Context
import androidx.room.Room
import androidx.transition.Visibility
import com.example.flashcard.database.CardDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        CardDatabase::class.java,
        "card_database"
    ).build()

    @Singleton
    @Provides
    fun provideDao(database: CardDatabase) = database.cardDatabaseDao()

}