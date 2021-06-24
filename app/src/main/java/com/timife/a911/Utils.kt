package com.timife.a911

import android.content.Context
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

object Utils {

    fun getJsonDataFromAsset(context: Context, fileName: Int): String? {
        val jsonString: String
        try {
            jsonString = context.resources.openRawResource(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }



}