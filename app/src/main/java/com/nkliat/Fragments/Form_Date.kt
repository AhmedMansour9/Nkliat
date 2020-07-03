package com.nkliat.Fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nkliat.R
import kotlinx.android.synthetic.main.fragment_form__date.view.*
import kotlinx.android.synthetic.main.fragment_form__from.view.*
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Form_Date.newInstance] factory method to
 * create an instance of this fragment.
 */
class Form_Date : Fragment() {
    lateinit var date: Calendar
    companion object{
        var Datee: String? = String()
        fun ValidateNo_Date():Boolean{
            if(Datee.isNullOrEmpty()){
                return false
            }else {
                return  true
            }
        }
    }
    var dateSetListener: DatePickerDialog.OnDateSetListener? = null

     lateinit var root:View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_form__date, container, false)
        GetDate()
        GetDateNow()

        return root
    }

    private fun GetDateNow() {
        root.Check_Date.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                val currentDateandTime: String = sdf.format(Date())
              Datee=currentDateandTime

            } else {
                Datee=null
            }
        }
    }

    fun GetDate() {
        root.T_ChooseDate.setOnClickListener(View.OnClickListener { showDateTimePicker() })
        dateSetListener =
            DatePickerDialog.OnDateSetListener { datePicker, i, i1, i2 -> root.T_ChooseDate.setText("$i-$i1-$i2") }
    }

    fun showDateTimePicker() {
        val currentDate = Calendar.getInstance()
        date = Calendar.getInstance()

        var dialog= DatePickerDialog(

            requireContext(),R.style.DialogTheme,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                date.set(year, monthOfYear, dayOfMonth)

                TimePickerDialog(
                    context, R.style.DialogTheme,
                    TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                        date.set(Calendar.HOUR_OF_DAY, hourOfDay)
                        date.set(Calendar.MINUTE, minute)
                        root.T_ChooseDate.setText("$year-"+(monthOfYear+1)+"-$dayOfMonth $hourOfDay:$minute")
                        if(monthOfYear.toString().length==1){
                            Datee="$year-"+(monthOfYear+1)+"-0$dayOfMonth $hourOfDay:$minute"

                        }else {
                            Datee="$year-"+(monthOfYear+1)+"-$dayOfMonth $hourOfDay:$minute"

                        }
                        if(monthOfYear.toString().length==1){
                            Datee="$year-"+"0"+(monthOfYear+1)+"-0$dayOfMonth $hourOfDay:$minute"

                        }else {
                            Datee="$year-"+(monthOfYear+1)+"-$dayOfMonth $hourOfDay:$minute"

                        }
                    },
                    currentDate[Calendar.HOUR_OF_DAY],
                    currentDate[Calendar.MINUTE],
                    false
                ).show()

            },
            currentDate[Calendar.YEAR],
            currentDate[Calendar.MONTH],
            currentDate[Calendar.DATE]

        )

        dialog.getDatePicker().setMinDate(date.getTimeInMillis());

        dialog.show()
    }


}