package android.tvz.hr.newz.di

import android.tvz.hr.newz.network.NewsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Singleton
    @Provides
    fun provideApiService(
        okHttpClient: OkHttpClient
    ): NewsService {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(NewsService::class.java)
    }
    @Singleton
    @Provides
    fun okHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }
}

private const val BASE_URL = "https://newsapi.org/v2/"