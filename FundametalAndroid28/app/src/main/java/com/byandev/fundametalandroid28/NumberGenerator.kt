package com.byandev.fundametalandroid28

import kotlin.random.Random

internal object NumberGenerator {

    fun generate(max: Int) : Int {
        val random = Random
        return random.nextInt(max)
    }

}