package com.nkliat.Fragments

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
import kotlinx.android.synthetic.main.fragment_form__to.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Form_To.newInstance] factory method to
 * create an instance of this fragment.
 */
class Form_To : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_form__to, container, false)
        openMap()
        root.E_NoRoom.setText(No_Rooms)
        root.E_TypeHouse.setText(TypeHouse)
        root.E_Floor.setText(No_Floor)
        root.E_NoRoom.setText(No_Rooms)
        if(!Address.isNullOrEmpty()) {
            root.T_Address.setText(Address)
        }
        return root
    }

    fun openMap(){
        root.T_Address.setOnClickListener(){
            var intent= Intent(context, Choose_Map::class.java)
            intent.putExtra("type","to_furntiture")
            startActivity(intent)
        }

    }
    companion object {
        lateinit var root:View
        var TypeHouse:String?= String()
        var No_Rooms:String?= String()
        var No_Floor:String?= String()
        var From_Lat:String?= String()
        var From_Lng:String?= String()
        var Address:String?= String()


        fun  ValidateTypeHouse():Boolean{
            TypeHouse=root.E_TypeHouse.text.toString()
            if(TypeHouse.isNullOrEmpty()){
                return false
            }else {
                return  true
            }
        }


        fun ValidateNo_Rooms():Boolean{
            No_Rooms=root.E_NoRoom.text.toString()
            if(No_Rooms.isNullOrEmpty()){
                return false
            }else {
                return  true
            }
        }

        fun ValidateNo_Floor():Boolean{
            No_Floor=root.E_Floor.text.toString()
            if(No_Floor.isNullOrEmpty()){
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

        fun ValidateLng():Boolean{
            if(From_Lng.isNullOrEmpty()){
                return false
            }else {
                return  true
            }
        }

    }


    @Subscribe(sticky = false, threadMode = ThreadMode.MAIN)
    fun onMessageEvent( messsg: MessageEvent) {/* Do something */
        Log.d("IGNORE", "Logging view to curb warnings: $messsg")
        if(messsg.Message.equals("to_furntiture")){
            root.T_Address.text=messsg.Address
            From_Lat=messsg.To_Lat
            From_Lng=messsg.To_Lng
            Address=messsg.Address
        }


    };
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }

    }

}