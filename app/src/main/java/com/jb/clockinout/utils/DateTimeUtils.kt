package com.jb.clockinout.utils

import android.content.Context

import java.text.SimpleDateFormat
import java.util.*
import java.text.ParseException


class DateTimeUtils {

    companion object {

        const val DATE_FORMAT: String = "MMM dd, yyyy hh:mm:ss a"

        fun getDateToday(): String {
            val formatter = SimpleDateFormat(DATE_FORMAT)
            return formatter.format(Date())
        }

        fun getCurrentDatetime(context: Context): String {
            var formattedDate: SimpleDateFormat = SimpleDateFormat(DATE_FORMAT)

            return formattedDate.format(Date())
        }

        fun convertDateToUnix(dateString: String): Long {
            val sdf = SimpleDateFormat(DATE_FORMAT)
            sdf.timeZone = TimeZone.getTimeZone("GMT")

            var date: Date? = null

            try {
                date = sdf.parse(dateString)
                return date!!.time / 1000
            } catch (e: ParseException) {
                e.printStackTrace()
            }


            return 0
        }
    }

}