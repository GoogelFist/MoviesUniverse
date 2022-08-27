package com.example.moviesuniverse.di.data

import android.app.Application
import androidx.room.Room
import com.example.moviesuniverse.data.local.movies.DataBase
import com.example.moviesuniverse.data.local.movies.MoviesDao
import org.koin.dsl.module

private const val DATABASE_NAME = "Movie data base"

val roomModule = module {

    fun provideDatabase(context: Application): DataBase {
        return Room.databaseBuilder(context, DataBase::class.java, DATABASE_NAME)
            .build()
    }

    fun provideDao(database: DataBase): MoviesDao {
        return database.getMovieDao()
    }

    single<DataBase> { provideDatabase(context = get()) }
    single<MoviesDao> { provideDao(database = get()) }
}