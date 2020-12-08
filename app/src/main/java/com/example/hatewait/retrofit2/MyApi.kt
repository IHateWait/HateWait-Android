package com.example.hatewait.retrofit2

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.CookieManager
import java.net.CookiePolicy


object MyApi {

    private const val BASE_URL = "https://hatewait-server.herokuapp.com/"

    data class onlyMessageResponseData(var message: String)

//    private fun retrofit(): Retrofit { // Singleton
//        return Retrofit.Builder().baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())// JSON
//            .build()
//    }

//    var cookieStore = PersistentCookieStore(MyApplication.getInstance())
//    var cookieManager: CookieManager = CookieManager(cookieStore, CookiePolicy.ACCEPT_ALL)

//    return new OkHttpClient().newBuilder()
//    .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
//    .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
//    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
//    .cookieJar(new JavaNetCookieJar(cookieManager))
//    .build();



    object RetrofitAdapter {

        fun retrofit(context: Context?): Retrofit? {
//            var cookieManager = CookieManager()
//            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL)
            var retrofit: Retrofit? = null
//            val cookieJar: CookieJar = PersistentCookieJar(
//                SetCookieCache(),
//                SharedPrefsCookiePersistor(context)
//            )
            val cookieStore = PersistentCookieStore(context)
            val cookieManager = CookieManager(cookieStore, CookiePolicy.ACCEPT_ALL)
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val builder: OkHttpClient.Builder = OkHttpClient.Builder()
//            builder.addNetworkInterceptor(AddCookiesInterceptor(context!!)) // VERY VERY IMPORTANT
//            builder.addInterceptor(ReceivedCookiesInterceptor(context!!)) // VERY VERY IMPORTANT

//            builder.cookieJar(javaNetCookieJar)
//                .cookieJar(JavaNetCookieJar(cookieManager))
            builder.addInterceptor(logging)
            builder.cookieJar(JavaNetCookieJar(cookieManager))
            var client = builder.build()
            if (retrofit == null) {
                retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).client(client).build()
            }

            return retrofit
        }
    }


//    val RegisterService: RetrofitRegister by lazy {
//        retrofit(context: Context?).create(RetrofitRegister::class.java)
//    }
//
//
//    val SignUpService: RetrofitSignUp by lazy {
//        retrofit().create(RetrofitSignUp::class.java)
//    }
//
//    val LoginService: RetrofitLogin by lazy {
//        retrofit().create(RetrofitLogin::class.java)
//    }
//
//    val WaitingService: RetrofitWaiting by lazy {
//        retrofit().create(RetrofitWaiting::class.java)
//    }
//
//    val CouponService: RetrofitCoupon by lazy {
//        retrofit().create(RetrofitCoupon::class.java)
//    }
//
//    val UpdateService: RetrofitInfoUpdate by lazy {
//        retrofit().create(RetrofitInfoUpdate::class.java)
//    }
//
//    val MapService: RetrofitMap by lazy {
//        retrofit().create(RetrofitMap::class.java)
//    }


//    class ReceivedCookiesInterceptor(context: Context) : Interceptor {
//        private val context: Context = context
//
//        @Throws(IOException::class)
//        override fun intercept(chain: Interceptor.Chain): Response {
//            val originalResponse = chain.proceed(chain.request())
//            if (originalResponse.headers("Set-Cookie").isNotEmpty()) {
//                val cookies = PreferenceManager.getDefaultSharedPreferences(context)
//                    .getStringSet("PREF_COOKIES", HashSet<String>()) as HashSet<String>
//                for (header in originalResponse.headers("Set-Cookie")) {
//                    cookies.add(header)
//                }
//                val memes: SharedPreferences.Editor =
//                    PreferenceManager.getDefaultSharedPreferences(context).edit()
//                memes.putStringSet("PREF_COOKIES", cookies).apply()
//                Log.i("PREF_COOKIES", cookies.toString())
//                memes.commit()
//            }
//            return originalResponse
//        }
//
//    }
//
//
//    class AddCookiesInterceptor(private val context: Context) : Interceptor {
//        @Throws(IOException::class)
//        override fun intercept(chain: Interceptor.Chain): Response {
//            val builder: Request.Builder = chain.request().newBuilder()
//            val preferences = PreferenceManager.getDefaultSharedPreferences(
//                context
//            ).getStringSet(PREF_COOKIES, HashSet()) as HashSet<String>
//            val original: Request = chain.request()
//            if (original.url().toString().contains("distributor")) {
//                for (cookie in preferences) {
//                    builder.addHeader("Cookie", cookie)
//                    Log.i("PREF_COOKIES", cookie)
//                }
//
//            }
//            return chain.proceed(builder.build())
//        }
//
//        companion object {
//            const val PREF_COOKIES = ""
//        }
//    }


}

