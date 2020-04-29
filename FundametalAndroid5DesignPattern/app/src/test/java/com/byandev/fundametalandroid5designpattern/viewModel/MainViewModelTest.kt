package com.byandev.fundametalandroid5designpattern.viewModel

import com.byandev.fundametalandroid5designpattern.model.CuboidModel
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mockito.*

class MainViewModelTest {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var cuboidModel: CuboidModel

    // contoh inputan edittext untuk menguji metode untuk menghitung volume dari sebuah balok
    private val dummyLength = 12.0
    private val dummyWidth = 7.0
    private val dummyHeight = 6.0

    private val dummyVolume = 504.0

    // contoh inputan edittext untuk menguji metode untuk menghitung luas permukaan dan keliling balok
    private val dummyCircumference = 100.0
    private val dummySurfaceArea = 396.0


    @Before // Fungsinya untuk menginisialisasi method sebelum melakukan test.
    // Method yang diberi anotasi @Before ini akan dijalankan sebelum menjalankan semua method dengan anotasi @Test
    fun setUp() {
        cuboidModel = mock(CuboidModel::class.java)
        mainViewModel = MainViewModel(cuboidModel)
    }

    @Test // Anotasi ini digunakan pada method yang akan dites.
    fun testVolume() {
        cuboidModel = CuboidModel()
        mainViewModel = MainViewModel(cuboidModel)
        mainViewModel.save(dummyWidth, dummyLength, dummyHeight)
        val volume = mainViewModel.getVolume()
        assertEquals(dummyVolume, volume, 0.0001)
    }

    @Test
    fun testCircumference() {
        cuboidModel = CuboidModel()
        mainViewModel = MainViewModel(cuboidModel)
        mainViewModel.save(dummyWidth, dummyLength, dummyHeight)
        val volume = mainViewModel.getCircumference()
        assertEquals(dummyCircumference, volume, 0.0001)
    }
    @Test
    fun tesSurfaceArea() {
        cuboidModel = CuboidModel()
        mainViewModel = MainViewModel(cuboidModel)
        mainViewModel.save(dummyWidth, dummyLength, dummyHeight)
        val volume = mainViewModel.getSurfaceArea()
        assertEquals(dummySurfaceArea, volume, 0.0001)
    }

    // Pengujian dengan library mock
    @Test
    fun testMockVolume() {
        `when`(mainViewModel.getVolume()).thenReturn(dummyVolume) // Digunakan untuk menandakan event
        // di mana Anda ingin memanipulasi behavior dari mock object.
        val volume = mainViewModel.getVolume()
        verify(cuboidModel).getVolume() // Digunakan untuk memeriksa metode dipanggil dengan arguman yang diberikan.
        // Verify merupkan fungsi dari framework Mockito
        assertEquals(dummyVolume, volume, 0.0001) // Fungsi ini merupakan fungsi dari JUnit
        // yang digunakan untuk memvalidasi output yang diharapkan dan output yang sebenarnya.
    }
    @Test
    fun testMockCircumference() {
        `when`(mainViewModel.getCircumference()).thenReturn(dummyCircumference)
        val circumference = mainViewModel.getCircumference()
        verify(cuboidModel).getCircumference()
        assertEquals(dummyCircumference, circumference, 0.0001)
    }
    @Test
    fun testMockSurfaceArea() {
        `when`(mainViewModel.getSurfaceArea()).thenReturn(dummySurfaceArea)
        val surfaceArea = mainViewModel.getSurfaceArea()
        verify(cuboidModel).getSurfaceArea()
        assertEquals(dummySurfaceArea, surfaceArea, 0.0001)
    }
}