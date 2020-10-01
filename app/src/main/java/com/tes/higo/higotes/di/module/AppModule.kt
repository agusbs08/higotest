package com.tes.higo.higotes.di.module

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.tes.higo.higotes.app.AppConfig
import com.tes.higo.higotes.app.AppPreference
import com.tes.higo.higotes.data.db.QuizDatabase
import com.tes.higo.higotes.data.network.RestAPI
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(application: Application): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BASIC

        val cacheDir = File(application.cacheDir, UUID.randomUUID().toString())
        // 10 MiB cache
        val cache = Cache(cacheDir, 10 * 1024 * 1024)

        return OkHttpClient.Builder()
            .cache(cache)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient) = Retrofit.Builder().baseUrl(AppConfig.BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideRetrofitService(retrofit: Retrofit) = retrofit.create(RestAPI::class.java)

    @Singleton
    @Provides
    fun provideQuizDatabase(context: Context) = Room.databaseBuilder(
        context,
        QuizDatabase::class.java,
        AppConfig.DB_NAME
    ).build()

    @Singleton
    @Provides
    fun provideUserDao(quizDatabase: QuizDatabase) = quizDatabase.getUserDao()

    @Singleton
    @Provides
    fun provideAppPreference(context: Context) = AppPreference(context)

}