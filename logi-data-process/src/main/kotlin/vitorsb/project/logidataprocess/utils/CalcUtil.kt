package vitorsb.project.logidataprocess.utils

import java.lang.Math

class CalcUtil {
    fun roundsValue(value: Double): Double {
        val decimalPlaces = 2
        val decimalPlacesFactor = Math.pow(10.0, decimalPlaces.toDouble())
        return Math.round(value * decimalPlacesFactor) / decimalPlacesFactor
    }
}