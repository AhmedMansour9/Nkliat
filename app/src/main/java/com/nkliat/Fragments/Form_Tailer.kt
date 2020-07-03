package com.nkliat.Fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nkliat.Activites.Choose_Map
import com.nkliat.Model.MessageEvent
import com.nkliat.R
import kotlinx.android.synthetic.main.fragment_form__tailer.*
import kotlinx.android.synthetic.main.fragment_form__tailer.view.*
import kotlinx.android.synthetic.main.fragment_form__tailer.view.T_AddressFrom
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Form_Tailer.newInstance] factory method to
 * create an instance of this fragment.
 */
class Form_Tailer : Fragment() {
   lateinit var root:View
    lateinit var date: Calendar
    companion object{
        var From_Lat:String?= String()
        var From_Lng:String?= String()
        var To_Lat:String?= String()
        var To_Lng:String?= String()
        var Descrption:String?= String()
        var CarType:String?= String()

        var Datee: String? = String()
        fun ValidateNo_Date():Boolean{
            if(Datee.isNullOrEmpty()){
                return false
            }else {
                return  true
            }
        }
        fun ValidateLat():Boolean{
            if(From_Lat.isNullOrEmpty()){
                return false
            }else {
                return  true
            }
        }
        fun ValidateToLat():Boolean{
            if(To_Lat.isNullOrEmpty()){
                return false
            }else {
                return  true
            }
        }
    }
    var dateSetListener: DatePickerDialog.OnDateSetListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_form__tailer, container, false)
        if(Trailer.Type.equals("flat_truck")){
         root.E_CarType.visibility=View.VISIBLE
         root.view5.visibility=View.VISIBLE
        }
        GetDate()
        GetDateNow()
        getAddressFrom()
        getAddressTo()

        return root
    }
    fun getAddressFrom(){
        root.T_AddressFrom.setOnClickListener(){
            var intent= Intent(context, Choose_Map::class.java)
            intent.putExtra("type","from_trailer")
            startActivity(intent)
        }
    }
    fun getAddressTo(){
        root.T_AddressTo.setOnClickListener(){
            var intent= Intent(context, Choose_Map::class.java)
            intent.putExtra("type","to_trailer")
            startActivity(intent)
        }

    }
    private fun GetDateNow() {
        root.Check_Date.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                val currentDateandTime: String = sdf.format(Date())
                Datee=currentDateandTime

            } else {
                Datee =null
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
                        Datee ="$year-"+(monthOfYear+1)+"-0$dayOfMonth $hourOfDay:$minute"

                    }else {
                        Datee ="$year-"+(monthOfYear+1)+"-$dayOfMonth $hourOfDay:$minute"

                    }
                    if(monthOfYear.toString().length==1){
                        Datee ="$year-"+"0"+(monthOfYear+1)+"-0$dayOfMonth $hourOfDay:$minute"

                    }else {
                        Datee ="$year-"+(monthOfYear+1)+"-$dayOfMonth $hourOfDay:$minute"

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

    @Subscribe(sticky = false, threadMode = ThreadMode.MAIN)
    fun onMessageEvent( messsg: MessageEvent) {/* Do something */
        Log.d("IGNORE", "Logging view to curb warnings: $messsg")
        if(messsg.Message.equals("from_trailer")){
            root.T_AddressFrom.text=messsg.Address
            From_Lat =messsg.From_Lat
            From_Lng =messsg.From_Lng
        }
        if(messsg.Message.equals("to_trailer")){
            root.T_AddressTo.text=messsg.Address
            To_Lat =messsg.To_Lat
            To_Lng =messsg.To_Lng

        }

    };
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }

    }

    override fun onDetach() {
        Descrption = root.E_Descrption.text.toString()
        CarType= root.E_CarType.text.toString()
        super.onDetach()
    }
}