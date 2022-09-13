package com.example.moviesuniverse.di.data

import android.app.Application
import androidx.room.Room
import com.example.moviesuniverse.data.local.DataBase
import com.example.moviesuniverse.data.local.movies.MoviesDao
import com.example.moviesuniverse.data.local.movies.MoviesDetailDao
import com.example.moviesuniverse.data.local.movies.RemoteKeysDao
import com.example.moviesuniverse.data.local.staff.MoviesStaffDao
import com.example.moviesuniverse.data.local.staff.StaffDetailDao
import org.koin.dsl.module

private const val DATABASE_NAME = "Movie data base"

val roomModule = module {

    fun provideDatabase(context: Application): DataBase {
        return Room.databaseBuilder(context, DataBase::class.java, DATABASE_NAME)
            .build()
    }

    fun provideMovieDao(database: DataBase): MoviesDao {
        return database.getMovieDao()
    }

    fun provideMovieStaffDao(database: DataBase): MoviesStaffDao {
        return database.getMovieStaffDao()
    }

    fun provideMovieDetailDao(database: DataBase): MoviesDetailDao {
        return database.getMovieDetailDao()
    }

    fun provideRemoteKeysDao(database: DataBase): RemoteKeysDao {
        return database.getRemoteKeyDao()
    }

    fun provideStaffDetailDao(database: DataBase): StaffDetailDao {
        return database.getStaffDetailDao()
    }

    single<DataBase> { provideDatabase(context = get()) }
    single<MoviesDao> { provideMovieDao(database = get()) }
    single<MoviesDetailDao> { provideMovieDetailDao(database = get()) }
    single<MoviesStaffDao> { provideMovieStaffDao(database = get()) }
    single<RemoteKeysDao> { provideRemoteKeysDao(database = get()) }
    single<StaffDetailDao> { provideStaffDetailDao(database = get()) }
}