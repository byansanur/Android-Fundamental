package com.byandev.fundametalandroid5designpattern.viewModel

import com.byandev.fundametalandroid5designpattern.model.CuboidModel

class MainViewModel(private val cuboidModel: CuboidModel) {
    // buat fungsi baru untuk memanggil fungsi yang sesuai pada model
    fun getCircumference(): Double = cuboidModel.getCircumference()
    fun getSurfaceArea(): Double = cuboidModel.getSurfaceArea()
    fun getVolume(): Double = cuboidModel.getVolume()
    fun save(l: Double, w: Double, h: Double) {
        cuboidModel.save(l, w, h)
    }
}