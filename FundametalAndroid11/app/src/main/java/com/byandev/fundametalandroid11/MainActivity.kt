package com.byandev.fundametalandroid11

import android.os.Bundle
import android.view.Menu
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

class MainActivity : AppCompatActivity() {

    /**
     * Penambahan fragment di navigation drawer yaitu :
     * 1. Tambahkan item menu di res -> menu -> activity_main_drawer, sesuaikan dengan kebutuhan
     * 2. Buat fragment baru untuk menampung sebagai View
     * 3. Atur tampilan fragment dengan sederhana terlebih dahulu
     * 4. Tambahkan codingan di res -> navigation -> mobile_navigation, sesuaikan dengan kebutuhan
     * 5. Atur appBarConfiguration untuk menghindari tertjadinya kesalahan tampilan
     */

    private lateinit var appBarConfiguration: AppBarConfiguration

    internal lateinit var profileCircleImageView: CircleImageView
    internal var profileImageUrl = "https://lh3.googleusercontent.com/-4qy2DfcXBoE/AAAAAAAAAAI/AAAAAAAABi4/rY-jrtntAi4/s640-il/photo.jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        /**
         * Obyek fab diberikan listener onClickListener() untuk menampilkan sebuah Snackbar.
         * Snackbar adalah suksesor dari toast. Namun tidak ada perbedaan diantara keduanya
         * dari sisi penggunaan.
         * Perbedaan mendasar adalah pada Snackbar Anda bisa menambahkan sebuah action
         * untuk melakukan sebuah aksi tertentu. Hal ini tidak bisa dilakukan pada toast.
         */
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        /**
         * drawerLayout merupakan rootView dari mainActivity
         * navView merupakan component yang di gunakan dalam drawer layout
         */
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)


        /**
         * NavigationView menampung semua menu dan sebuah header.
         * Karena itulah jika Anda ingin mengubah komponen view yang terdapat di dalam header sebuah navigation view,
         * maka proses casting/inisialisasi komponen harus dilakukan dengan cara seperti ini:
         *
         * Kenapa harus 0? Ini karena indeks header berada pada susunan teratas dari kumpulan list menu yang terdapat pada NavigationView.
         */
        profileCircleImageView = navView.getHeaderView(0).findViewById(R.id.imageView)
        Glide.with(this)
            .load(profileImageUrl)
            .into(profileCircleImageView)

        /**
         * AppBarConfiguration berisi kumpulan id yang ada di dalam menu NavigationDrawer (baris 3).
         * Jika id yang ada di dalam menu NavigationDrawer ditambahkan di AppBarConfiguration,
         * maka AppBar akan berbentuk Menu NavigationDrawer.
         * Jika tidak ditambahkan, maka akan menampilkan tanda panah kembali.
         */
        appBarConfiguration = AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_tools, R.id.nav_chart,
                R.id.nav_share, R.id.nav_send,
                R.id.nav_map, R.id.nav_hotel, R.id.nav_subway)
            .setDrawerLayout(drawerLayout)
            .build()

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        /**
         * setupActionBarWithNavController digunakan untuk mengatur judul AppBar agar
         * sesuai dengan Fragment yang ditampilkan.
         */
        setupActionBarWithNavController(navController, appBarConfiguration)

        /**
         * setupWithNavController digunakan supaya NavigationDrawer menampilkan Fragment yang sesuai
         * ketika menu dipilih.
         */
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        // menu main untuk menu action bar titik tiga vertikal
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
