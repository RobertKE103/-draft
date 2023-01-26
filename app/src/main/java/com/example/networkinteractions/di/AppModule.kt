package com.example.networkinteractions.di

import android.content.Context
import androidx.room.Room
import com.example.networkinteractions.Utils.BASE_URL
import com.example.networkinteractions.data.database.AppDatabase
import com.example.networkinteractions.data.database.NewsFavoriteDao
import com.example.networkinteractions.data.database.NewsThemeDao
import com.example.networkinteractions.data.network.EverythingNewsPageSource
import com.example.networkinteractions.data.network.NewsService
import com.simplemented.okdelay.DelayInterceptor
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/*
* Объект AppModule аннотирован @InstallIn(SingletonComponent::class),
* что означает, что привязки, определенные в этом модуле,
* доступны в контейнере Application.
 */

/*
* @InstallIn(SingletonComponent::class)
* Эта аннотация сообщает Hilt, что зависимости, предоставляемые через этот модуль,
* должны оставаться живыми до тех пор, пока работает приложение.
*/

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideOkhttpClient(): OkHttpClient =
        OkHttpClient()
            .newBuilder()
            .addInterceptor(DelayInterceptor(15L, TimeUnit.MINUTES))
            .build()

    @Singleton
    @Provides
    fun provideNewsApi(retrofit: Retrofit): NewsService =
        retrofit.create(NewsService::class.java)

    @Provides
    fun provideNewsFavoriteDao(appDatabase: AppDatabase): NewsFavoriteDao = appDatabase.newsFavoriteDao()

    @Provides
    fun provideNewsThemeDao(appDatabase: AppDatabase): NewsThemeDao = appDatabase.newsTheme()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context) : AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "News.db"
        ).build()
    }




}