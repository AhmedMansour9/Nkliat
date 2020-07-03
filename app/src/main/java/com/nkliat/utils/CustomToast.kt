package com.tayser.utils

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.nkliat.R

class CustomToast {
    companion object {

     fun toastIconError(message:String,context:Activity) {
        val toast = Toast(context)
        toast.duration = Toast.LENGTH_LONG

        //inflate view
        val custom_view: View =
            context.getLayoutInflater().inflate(R.layout.toast_icon_text, null)
        (custom_view.findViewById<View>(R.id.message) as TextView).text = "$message"
        (custom_view.findViewById<View>(R.id.icon) as ImageView).setImageResource(
            R.drawable.ic_close
        )
        (custom_view.findViewById<View>(R.id.parent_view) as CardView).setCardBackgroundColor(
            context.getResources().getColor(R.color.red_600)
        )
        toast.view = custom_view
        toast.show()
    }

        fun toastIconError(message:String,context:Context) {
            val toast = Toast(context)
            toast.duration = Toast.LENGTH_LONG

            //inflate view
            val custom_view: View =
                LayoutInflater.from(context).inflate(R.layout.toast_icon_text, null)
            (custom_view.findViewById<View>(R.id.message) as TextView).text = "$message"
            (custom_view.findViewById<View>(R.id.icon) as ImageView).setImageResource(
                R.drawable.ic_close
            )
            (custom_view.findViewById<View>(R.id.parent_view) as CardView).setCardBackgroundColor(
                context.getResources().getColor(R.color.red_600)
            )
            toast.view = custom_view
            toast.show()
        }
     fun toastIconSuccess(message:String,context:Activity) {
        val toast = Toast(context)
        toast.duration = Toast.LENGTH_LONG

        //inflate view
        val custom_view: View =
            context.getLayoutInflater().inflate(R.layout.toast_icon_text, null)
        (custom_view.findViewById<View>(R.id.message) as TextView).text = "$message"
        (custom_view.findViewById<View>(R.id.icon) as ImageView).setImageResource(
            R.drawable.ic_done
        )

        (custom_view.findViewById<View>(R.id.parent_view) as CardView).setCardBackgroundColor(
            context.getResources().getColor(R.color.green_500)
        )
        toast.view = custom_view
        toast.show()
    }


    }
}