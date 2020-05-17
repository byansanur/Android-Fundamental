package com.byandev.fundametalandroid24

import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.text.DecimalFormat

class ActivityViewModel : ViewModel() {

    /*
    Notes :
    Terdapat LiveData dan MutableLiveData. Lalu apa bedanya? Keduanya sebenarnya mirip,
    namun bedanya MutableLiveData bisa kita ubah value-nya, sedangkan LiveData bersifat read-only.
     */

    val listWeathers = MutableLiveData<ArrayList<WeatherItems>>()

    fun setWeather(city: String) {
        // request API
        val listItems = ArrayList<WeatherItems>()

        val apiKey = "fb161587654f69bd97613e2be8c32a25"
        val url = "https://api.openweathermap.org/data/2.5/group?id=$city&units=metric&appid=${apiKey}"

        val client = AsyncHttpClient()
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                try {
                    //parsing json
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("list")

                    // Perhatikan cara konverter dari JSONObject menjadi sebuah data yang bisa dibaca dengan mudah:
                    /*
                    Di sini kita melakukan perulangan sejumlah data yang ada, kemudian data pada JSON Object
                    dibaca berdasarkan key-nya. Seperti contoh pada kode di bawah,
                    ketika Anda membutuhkan value name maka Anda mencari value-nya dengan
                    mencocokan key yang sesuai, misalnya “name”.
                     */
                    for (i in 0 until list.length()) {
                        val weather = list.getJSONObject(i)
                        val weatherItems = WeatherItems()
                        weatherItems.id = weather.getInt("id")
                        weatherItems.name = weather.getString("name")
                        weatherItems.currentWeather = weather.getJSONArray("weather").getJSONObject(0).getString("main")
                        weatherItems.description = weather.getJSONArray("weather").getJSONObject(0).getString("description")
                        val tempInKelvin = weather.getJSONObject("main").getDouble("temp")
                        val tempInCelsius = tempInKelvin - 273
                        weatherItems.temperature = DecimalFormat("##.##").format(tempInCelsius)
                        listItems.add(weatherItems)
                    }

                    listWeathers.postValue(listItems)
                    /*
                    Kita bisa menyisipkan perubahan yang terjadi dengan postValue().
                    Jadi secara realtime MutableLiveData akan menerima data yang baru.
                    Lalu apa bedanya antara postValue(), getValue() dan setValue().

                    // Perbedaannya
                    - setValue()
                        Menetapkan sebuah nilai dari LiveData. Jika ada observer yang aktif,
                        nilai akan dikirim kepada mereka. Metode ini harus dipanggil dari main thread.
                    - postValue()
                        Posting tugas ke main thread untuk menetapkan nilai yang diberikan dari background thread,
                        seperti melalui dalam kelas MainViewModel. Jika Anda memanggil metode ini beberapa kali
                        sebelum main thread menjalankan tugas yang di-posting,
                        hanya nilai terakhir yang akan dikirim.
                    - getValue()
                        Mendapatkan nilai dari sebuah LiveData.
                     */
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }

            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }
    fun getWeathers(): LiveData<ArrayList<WeatherItems>> {
        return listWeathers
    }

}