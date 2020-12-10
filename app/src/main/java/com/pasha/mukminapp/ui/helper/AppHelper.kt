package com.pasha.mukminapp.ui.helper

import java.util.*

class AppHelper {
    companion object{
        const val BASE_URL = "http://mukmin.retno.eu"
        val calendar = Calendar.getInstance()

        fun getDateNow(): String {
            val year = getYear()
            val month = getMonth()
            val day = getDay()
            val date = getDate()

            val nama_bulan = arrayOf("Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember")
            val nama_hari = arrayOf("Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu", "Minggu")

            return nama_hari[day-2]+", "+date+" "+nama_bulan[month]
        }

        fun getYear(): Int{
            return calendar.get(Calendar.YEAR)
        }

        fun getMonth(): Int{
            return calendar.get(Calendar.MONTH)
        }

        fun getDay(): Int{
            return calendar.get(Calendar.DAY_OF_WEEK)
        }

        fun getDate(): Int{
            return calendar.get(Calendar.DAY_OF_MONTH)
        }
    }
}