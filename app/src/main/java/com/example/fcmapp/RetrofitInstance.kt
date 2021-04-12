package layout

import layout.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    companion object {
        private val retrofit by lazy {
            val clientBuilder = OkHttpClient.Builder()
            clientBuilder.addInterceptor(HttpLoggingInterceptor().also {
                it.level = HttpLoggingInterceptor.Level.BODY
            })
            Retrofit.Builder().client(clientBuilder.build()).baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build()
        }

        val api by lazy {
            retrofit.create(NotificationAPI::class.java)
        }
    }
}