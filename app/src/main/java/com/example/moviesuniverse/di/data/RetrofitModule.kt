package com.example.moviesuniverse.di.data

import com.example.moviesuniverse.data.remote.movies.MoviesRetrofitService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit


private const val BASE_URL = "https://kinopoiskapiunofficial.tech/api/v2.2/films/"

private const val HEADER_API_KEY = "X-API-KEY"
private const val AUTH_TOKEN = "f1702a66-df70-4672-824c-77df35fd6d3f"

private const val HEADER_CONTENT_TYPE = "Content-Type"
private const val CONTENT_TYPE = "application/json"

private val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
    encodeDefaults = false
}

@OptIn(ExperimentalSerializationApi::class)
val retrofitModule = module {

    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    fun provideCustomInterceptor(): Interceptor {
        return Interceptor { chain ->
            val newRequest = chain.request()
                .newBuilder()
                .addHeader(HEADER_API_KEY, AUTH_TOKEN)
                .addHeader(HEADER_CONTENT_TYPE, CONTENT_TYPE)
                .build()
            chain.proceed(newRequest)
        }
    }

    fun provideHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        customInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(customInterceptor)
            .build()
    }

    fun provideRetrofit(client: OkHttpClient): Retrofit {
        val contentType = CONTENT_TYPE.toMediaType()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .client(client)
            .build()
    }

    fun provideMovieRetrofitService(retrofit: Retrofit): MoviesRetrofitService {
        return retrofit.create(MoviesRetrofitService::class.java)
    }

    // TODO: will check it
    factory<HttpLoggingInterceptor> { provideHttpLoggingInterceptor() }
    factory<Interceptor> { provideCustomInterceptor() }
    factory<OkHttpClient> {
        provideHttpClient(loggingInterceptor = get(), customInterceptor = get())
    }
    single<Retrofit> { provideRetrofit(client = get()) }
    single<MoviesRetrofitService> { provideMovieRetrofitService(retrofit = get()) }
}

