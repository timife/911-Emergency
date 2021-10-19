package com.timife.a911

import android.app.Activity
import android.content.Context
import android.content.Intent
import java.io.IOException

object Utils {

    fun getJsonDataFromAsset(context: Context, fileName: Int): String? {
        val jsonString: String
        try {
            jsonString =
                context.resources.openRawResource(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }


}
fun <A : Activity> Activity.startNewActivity(activity: Class<A>) {
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}