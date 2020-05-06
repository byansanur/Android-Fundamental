package com.byandev.fundametalandroid13

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter (private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val TAB_TITLES = intArrayOf(
        R.string.tab_text_1,
        R.string.tab_text_2,
        R.string.tab_text_3
    )

    override fun getItem(position: Int): Fragment {
        /*
         Mengirim Data ke Fragment :
         Untuk menggunakan TabLayout dengan hanya satu Fragment,
         Anda bisa mensiasatinya dengan mengirim data ke Fragment tersebut.
         Penggunaan satu fragment ini digunakan jika data yang ditampilkan mirip,
         sehingga tidak perlu membuat banyak fragment dan layout.
         Namun jika layout yang ditampilkan sangat berbeda,
         lebih baik membuat fragment baru saja.
         */
        val fragment = HomeFragment.newInstance(position + 1)
        return fragment

        /*
        var fragment: Fragment? = null
        when(position) {
            0 -> fragment = HomeFragment()
            1 -> fragment = ProfileFragment()
        }
        return fragment as Fragment
         */
    }

    /*
    getPageTitle digunakan untuk memberikan judul pada masing-masing tab,
    di sini Anda menggunakan teks yang berasal dari resource Strings.xml
     */
    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    /*
    getCount digunakan untuk menentukan jumlah tab yang ingin ditampilkan.
    Pada kode di bawah, Anda mencoba untuk menampilkan tiga tab.
     */
    override fun getCount(): Int {
        return 3
    }

}