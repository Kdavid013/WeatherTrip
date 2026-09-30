import com.example.weathertrip.network.DirectionsApiService
import com.example.weathertrip.network.OpenMeteoApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.getValue
import kotlin.jvm.java

object NetworkModule {

    // 1. Közös OkHttpClient (Connection pool, timeoutok újrahasznosítása memória-megtakarításért)
    private val okHttpClient: OkHttpClient by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    // 2. Mapbox Retrofit példány & Szerviz
    private val mapboxRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.mapbox.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val mapboxApi: DirectionsApiService by lazy {
        mapboxRetrofit.create(DirectionsApiService::class.java)
    }

    // 3. Open-Meteo Retrofit példány & Szerviz
    private val openMeteoRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val openMeteoApi: OpenMeteoApi by lazy {
        openMeteoRetrofit.create(OpenMeteoApi::class.java)
    }
}