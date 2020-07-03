package com.nkliat.Fragments

import android.app.Activity
import android.content.SharedPreferences
import android.graphics.PorterDuff
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.nkliat.Activites.Navigation
import com.nkliat.Loading
import com.nkliat.Model.CreateOrder_Response
import com.nkliat.Model.FurntiuretransferRequest_Model
import com.nkliat.R
import com.nkliat.ViewModel.RequestFurntiure_ViewModel
import com.tayser.utils.CustomToast
import kotlinx.android.synthetic.main.fragment_trailer.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
lateinit var Tag:String

/**
 * A simple [Fragment] subclass.
 * Use the [Trailer.newInstance] factory method to
 * create an instance of this fragment.
 */
class Trailer : Fragment() {
    lateinit var root:View
    private var idx_state = 0
    var array_state= arrayOf<String>()
    var bundle=Bundle()
    private lateinit var DataSaver: SharedPreferences
    companion object {
      var Type:String?= String()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_trailer, container, false)
        bundle=this.requireArguments()
        Type=bundle.getString("type")
        DataSaver = PreferenceManager.getDefaultSharedPreferences(requireContext().applicationContext)
        init()
        Tag="from"
        displayFragment(resources.getString(R.string.from))
        return root
    }
    fun CreatOrder(){
        var Furntiure= FurntiuretransferRequest_Model(
            bundle.getString("type"),
            Form_Tailer.Datee,
            null,
            null,
            null,
            null,
            null,
            Form_Tailer.Descrption,
            Form_Tailer.From_Lat,
            Form_Tailer.From_Lng,
            Form_Tailer.To_Lat,
            "create",
            Form_Tailer.To_Lng,
//            null,
//            null,
//            null,
//            null,
            Form_Tailer.CarType
        )

        Loading.Show(requireContext())
        var Sizes: RequestFurntiure_ViewModel =  ViewModelProvider.NewInstanceFactory().create(
            RequestFurntiure_ViewModel::class.java)
        requireContext().applicationContext?.let {
            Sizes.getData(Furntiure, DataSaver.getString("token", null)!!, it)
                .observe(viewLifecycleOwner, Observer<CreateOrder_Response> { loginmodel ->
                    Loading.Disable()
                    if(loginmodel.message.equals("Added successfully")) {
                        root.image_from.clearColorFilter()
                        root.image_to.clearColorFilter()
                        root.lyt_finish.visibility = View.VISIBLE
                        root.lyt_Send.visibility = View.GONE
                        val fragmentManager: FragmentManager =
                            requireActivity().supportFragmentManager
                        val fragmentTransaction =
                            fragmentManager.beginTransaction()
                        var fragment: Fragment? = null
                        fragment = SuccessOrder()
                        Tag = "success"
                        fragmentTransaction.replace(R.id.frame_content, fragment, Tag)
                        fragmentTransaction.commit()
                    }
                })
        }

    }
    private fun init() {
        if(Type.equals("trailer")){
            Navigation.T_Title.text=resources.getString(R.string.trailer)
        }
        if(Type.equals("light_trailer")){
            Navigation.T_Title.text=resources.getString(R.string.l_trailter)
        }
        if(Type.equals("pickup")){
            Navigation.T_Title.text=resources.getString(R.string.pickup)
        }
        if(Type.equals("flat_truck")){
            Navigation.T_Title.text=resources.getString(R.string.flattruck)
        }
        array_state=  arrayOf(resources.getString(R.string.from),resources.getString(R.string.to))
        root.image_to.setColorFilter(
            ContextCompat.getColor(requireContext(),R.color.grey_10),
            PorterDuff.Mode.SRC_ATOP
        )

        root.lyt_Send.setOnClickListener {
            ValidateForm_Date()


        }

    }


    private fun displayFragment(state: String) {
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction =
            fragmentManager.beginTransaction()
        var fragment: Fragment? = null
        refreshStepTitle()
        if (state.equals(

                resources.getString(R.string.from),
                ignoreCase = true
            )
        ) {
            root.Title.text= resources.getString(R.string.from)
            fragment = Form_Tailer()
            Tag="from"
            root.tv_from.setTextColor(ContextCompat.getColor(requireContext(),R.color.grey_90))
            root.image_from.clearColorFilter()
            root.line_first.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.grey_10))
            root.image_to.setColorFilter(
                ContextCompat.getColor(requireContext(),R.color.grey_10),
                PorterDuff.Mode.SRC_ATOP
            )
            root.line_first.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.grey_10))
            root.image_to.setColorFilter(
                ContextCompat.getColor(requireContext(),R.color.grey_10),
                PorterDuff.Mode.SRC_ATOP
            )
            root.line_second.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.grey_10))

        }

        if (fragment == null) return
        fragmentTransaction.replace(R.id.frame_content, fragment,Tag)
        fragmentTransaction.commit()
    }
    private fun refreshStepTitle() {
        root.tv_from.setTextColor(ContextCompat.getColor(requireContext(),R.color.grey_20))
        root.tv_to.setTextColor(ContextCompat.getColor(requireContext(),R.color.grey_20))
    }



    fun ValidateForm_Date(){
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val Fragment  =
            fragmentManager.findFragmentByTag("from")
        if (Fragment != null && Fragment.isVisible()) {
            if(!Form_Tailer.ValidateNo_Date() or !Form_Tailer.ValidateLat() or !Form_Tailer.ValidateToLat()
            ){
                CustomToast.toastIconError(resources.getString(R.string.validate_feild),
                    context as Activity
                )
            }else {
                CreatOrder()
            }
        }
    }



}