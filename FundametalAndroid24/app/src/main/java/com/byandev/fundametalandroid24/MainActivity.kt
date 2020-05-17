package com.byandev.fundametalandroid24

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.byandev.fundametalandroid24.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: WeatherAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: ActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // viewBinding use
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.getRoot())
        // Menghubungkan viewModel dengan activity
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(ActivityViewModel::class.java)
        // penghubung class adapter dengan activity untuk interaksi dengan recyclerView
        adapter = WeatherAdapter()
        adapter.notifyDataSetChanged()
        // pemanggilan id dengan viewBinding
        listener()
    }

    private fun listener() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        binding.btnCity.setOnClickListener{
            // validasi isian editText
            val city = binding.editCity.text.toString()
            if (city.isEmpty()) return@setOnClickListener
            // setelah validasi show progressBar
            showLoading(true)
            // interaksi dengan function pada viewModel
            mainViewModel.setWeather(city)
        }
        // Subscribe untuk mendapatkan value dari LiveData yang ada pada kelas viewModel
        mainViewModel.getWeathers()
            .observe(this, Observer {
                if (it != null) {
                    adapter.setData(it)
                    showLoading(false)
                }
            })
    }

    private fun showLoading(state: Boolean) {
        if (state) binding.progressBar.visibility = View.VISIBLE
        else binding.progressBar.visibility = View.GONE
    }

    // sebelum menggunakan viewModel
    /*
    private fun setWeather(city: String) {
        val listItems = ArrayList<WeatherItems>()
        val apiKey = getString(R.string.APP_ID)
        val url = "https://api.openweathermap.org/data/2.5/group?id=$city&units=metric&appid=${apiKey}"

        val client = AsyncHttpClient()
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                try {
                    //parsing json
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("list")
                    for (i in 0 until list.length()) {

                        val weather = list.getJSONObject(i)

                        weatherItems.id = weather.getInt("id")
                        weatherItems.name = weather.getString("name")
                        weatherItems.currentWeather = weather.getJSONArray("weather").getJSONObject(0).getString("main")
                        weatherItems.description = weather.getJSONArray("weather").getJSONObject(0).getString("description")

                        val tempInKelvin = weather.getJSONObject("main").getDouble("temp")
                        val tempInCelsius = tempInKelvin - 273

                        weatherItems.temperature = DecimalFormat("##.##").format(tempInCelsius)
                        listItems.add(weatherItems)

                    }
                    //set data ke adapter
                    adapter.setData(listItems)
                    showLoading(false)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                Log.d("onFailure", error.message.toString())
            }

        })
    }
     */
}
