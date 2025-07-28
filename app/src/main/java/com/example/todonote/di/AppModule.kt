package com.example.todonote.di
/*

import android.content.Context
import androidx.room.Room
import com.example.todonote.data.local.NoteDao
import com.example.todonote.data.local.NoteDatabase
import com.example.todonote.domain.repository.NoteRepository
import com.example.todonote.domain.usecase.AddNoteUseCase
import com.example.todonote.domain.usecase.GetNotesUseCase
import com.example.todonote.domain.usecase.UseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): NoteDatabase {
        return Room.databaseBuilder(
            context,
            NoteDatabase::class.java,
            "note_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteDao(db: NoteDatabase): NoteDao {
        return db.noteDao()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(noteDao: NoteDao): NoteRepository {
        return NoteRepository(noteDao)
    }
    @Provides
    @Singleton
    fun provideUseCases(repository: NoteRepository): UseCases {
        return UseCases(
            addNote = AddNoteUseCase(repository),
            getNotes = GetNotesUseCase(repository)
        )
    }

}*/
